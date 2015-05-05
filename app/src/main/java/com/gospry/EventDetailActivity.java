package com.gospry;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.gospry.generate.Event;
import com.gospry.generate.Keyword;
import com.gospry.generate.LocalSession;
import com.gospry.remote.RemoteCallback;
import com.gospry.remote.RemoteResponse;
import com.gospry.remote.serialize.InvitationSerializer;
import com.gospry.remote.state.GetEventInvitationState;
import com.gospry.remote.state.GetEventState;
import com.gospry.suggestion.Suggestion;
import com.gospry.suggestion.SuggestionContact;
import com.gospry.suggestion.SuggestionTypes;
import com.gospry.util.C;
import com.gospry.view.TagListView;
import com.shamanland.fab.FloatingActionButton;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.LinkedList;
import java.util.List;


public class EventDetailActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    TagListView addedfriends;
    List<TagListView.Tag> taglist = new LinkedList<TagListView.Tag>();
    GoogleApiClient mGoogleApiClient;
    private boolean isCreator;
    private boolean isModerator;
    private Long remote_eventId;
    private AppCtx context;

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        buildGoogleApiClient();
        MeetMeApp app = (MeetMeApp) getApplication();
        context = app.getCtx();

        remote_eventId = getIntent().getLongExtra(C.EXTRA_REMOTE_EVENT_ID, -1);
        final Long eventId = getIntent().getLongExtra(C.EXTRA_EVENT_ID, -1);
        if (eventId == -1 || remote_eventId == -1) {
            throw new IllegalStateException("detail view did not get an event id");
        }

        final Event event = app.getSession().findEvent(eventId);

        ViewHolder holder = new ViewHolder(findViewById(R.id.background));
        holder.update(event, app.getSession());

        GetEventState state = new GetEventState(context, remote_eventId);
        state.setCallback(new RemoteCallback() {
            @Override
            public void onRequestOk(RemoteResponse response) {
                // here you are able to extract additional information about the event
            }

            @Override
            public void onRequestFailed(RemoteResponse response) {
            }
        });
        state.start();
        GetEventInvitationState state2 = new GetEventInvitationState(context, remote_eventId);
        state2.setCallback(new RemoteCallback() {
            @Override
            public void onRequestOk(RemoteResponse response) {
                // here you are able to extract additional information about the event
                getLayoutInflater().inflate(R.layout.activity_event_detail, null);
                String test = ((MeetMeApp) getApplication()).getAccount().getNumber();
                if (event.getUser().equals(test)) {
                    isCreator = true;
                    //TODO: Context Menu is not on Tags but daneben, einzeln setzen oder so
                }
                JsonArray array = response.getJsonArray();
                InvitationSerializer serializer = new InvitationSerializer();
                for (int i = 0; i < array.size(); i++) {
                    JsonObject object = array.get(i).getAsJsonObject();
                    Invitation invitation = serializer.deserialize(object);
                    invitation.setStatus(object.get("status").toString());
                    SuggestionContact add = new SuggestionContact(invitation.getUsername(getApplicationContext()), SuggestionTypes.PERSON, invitation.getNumber());
                    TagListView.Tag tag;
                    //TODO:Test this

                    if (((MeetMeApp) getApplication()).getAccount().getNumber() == invitation.getNumber()) {
                        if (invitation.isModerator()) {
                            isModerator = true;
                        }
                    }
                    switch (invitation.getStatus()) {
                        case INVITED:
                            tag = new TagListView.Tag(add, R.color.gray);
                            break;
                        case NOT_ATTENDING:
                            tag = new TagListView.Tag(add, R.color.red);
                            break;
                        case ATTENDING:
                            tag = new TagListView.Tag(add, R.color.green);
                            break;
                        default:
                            tag = new TagListView.Tag(add, R.color.gray);
                    }
                    taglist.add(tag);
                }

            }

            @Override
            public void onRequestFailed(RemoteResponse response) {
            }
        });
        state2.start();
    }
    @Override
    public void onStart() {
        super.onStart();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addfriends);
        addedfriends = (TagListView) findViewById(R.id.addedfriends);
        if (isCreator == true || isModerator == true) fab.setVisibility(View.VISIBLE);
        if (isCreator == true) registerForContextMenu(addedfriends);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(EventDetailActivity.this, AddFriendsActivity.class);
                intent.putExtra("RemoteEventId", remote_eventId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MeetMeApp app = (MeetMeApp) getApplication();
                app.startActivity(intent);
            }
        });
        fab.setVisibility(View.VISIBLE);
        for (TagListView.Tag tag : taglist) {
            addedfriends.addTag(tag);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_event_detail, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.remove:
                //TODO: UnINvite
                return true;
            case R.id.make_moderator:
                ;
                //TODO: make a moderator
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("Location", "Location services connected.");
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Log.i("Location", location.getLatitude() + " " + location.getLongitude());
        final MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setClickable(false);
        mapView.setBuiltInZoomControls(true);
        // setContentView(mapView); //displaying the MapView
        mapView.getController().setZoom(22); //set initial zoom-level, depends on your need
        GeoPoint myLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
        mapView.getController().setCenter(myLocation); //This point is in Enschede, Netherlands. You should select a point in your map or get it from user's location.
        mapView.setUseDataConnection(true); //keeps the mapView from loading online tiles using network connection.
        //TODO: Onclick open google maps maybe? Display a marker where user is at
      /*  SimpleLocationOverlay mMyLocationOverlay = new SimpleLocationOverlay(this);
        Bitmap bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        mMyLocationOverlay.setLocation(myLocation);
        mMyLocationOverlay.draw(canvas,mapView,false);
        mapView.addView(mMyLocationOverlay.);

        List<OverlayItem> overlayItemArray = new ArrayList<OverlayItem>();

        DefaultResourceProxyImpl defaultResourceProxyImpl
                = new DefaultResourceProxyImpl(this);
        ItemizedIconOverlay<OverlayItem> myItemizedIconOverlay
                = new ItemizedIconOverlay<OverlayItem>(overlayItemArray, null, defaultResourceProxyImpl);
        mapView.getOverlays().add(myItemizedIconOverlay);
        overlayItemArray.clear();

        OverlayItem newMyLocationItem = new OverlayItem(
                "My Location", "My Location", myLocation);
        overlayItemArray.add(newMyLocationItem);*/
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("Location", "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, C.CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("Location", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    private class ViewHolder {

        private final View view;
        private final TextView desc;
        private final TextView when;
        private final TextView where;
        private final TagListView keywords;


        @SuppressLint("WrongViewCast")
        public ViewHolder(View view) {
            this.view = view;
            this.desc = (TextView) view.findViewById(R.id.desc);
            this.keywords = (TagListView) view.findViewById(R.id.keywords);
            this.when = (TextView) view.findViewById(R.id.when);
            this.where = (TextView) view.findViewById(R.id.where);
        }

        public void update(Event event, LocalSession session) {
            desc.setText(event.getDescription());
            where.setText(event.getLocation());
            when.setText(event.getStartTime().toString());
            List<Keyword> kws = event.loadKeywords(session).all();
            for (Keyword word : kws) {
                keywords.addTag(new TagListView.Tag(new Suggestion(word.getText(), SuggestionTypes.of(word.getText()))));
            }
        }
    }
}
