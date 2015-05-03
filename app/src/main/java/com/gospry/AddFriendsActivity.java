package com.gospry;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gospry.remote.RemoteCallback;
import com.gospry.remote.RemoteResponse;
import com.gospry.remote.RemoteState;
import com.gospry.remote.state.PostInviteUserState;
import com.gospry.suggestion.Suggestion;
import com.gospry.suggestion.SuggestionContact;
import com.gospry.suggestion.SuggestionEngine;
import com.gospry.suggestion.SuggestionTypes;
import com.gospry.util.C;
import com.gospry.util.Response;
import com.gospry.view.TagListView;
import com.shamanland.fab.FloatingActionButton;

import java.util.List;

/**
 * Created by rich on 21.11.14.
 */
public class AddFriendsActivity extends Activity {

    final static Response outresponse = new Response();
    private static final int CONTACT_PICKER_RESULT = 1001;
    private TagListView friendTags;
    private TagListView sel_friendTags;
    private long remoteEventId;
    private TagListView.OnTagClickListener suggestionClickListener = new TagListView.OnTagClickListener() {
        @Override
        public void onTagClick(TagListView.Tag tag) {
            tag.setActivated(true);
            sel_friendTags.addTag(tag);
            friendTags.removeTag(tag);
            Bundle bundle = new Bundle();
            bundle.putParcelable(C.EXTRA_LAST_ADDED, tag.getObject());
        }
    };
    private TagListView.OnTagClickListener selectedClickListener = new TagListView.OnTagClickListener() {
        @Override
        public void onTagClick(TagListView.Tag tag) {
            tag.setActivated(false);
            sel_friendTags.removeTag(tag);
            friendTags.addTag(tag);
        }
    };

    public void setResponse(RemoteResponse response) {
        outresponse.setResponse(response.getJsonObject());
        remoteEventId = outresponse.getResponse().get("id").getAsLong();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myFragmentView = inflater.inflate(R.layout.activity_add_friends, container, false);


        return myFragmentView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        remoteEventId = getIntent().getLongExtra("RemoteEventId", 2);
        if (remoteEventId == 2) //TODO: remoteevent id error here
            getLayoutInflater().inflate(R.layout.activity_add_friends, null);
        friendTags = (TagListView) findViewById(R.id.friend_list);
        sel_friendTags = (TagListView) findViewById(R.id.sel_friend_list);

        Button addfriendbutton = (Button) findViewById(R.id.addfriendsbutton);
        addfriendbutton.setVisibility(View.VISIBLE);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabaddfriends);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLaunchContactPicker(v);
            }
        });

        friendTags.setListener(suggestionClickListener);
        sel_friendTags.setListener(selectedClickListener);
        savedInstanceState = new Bundle();
        savedInstanceState.putInt(C.SUGGESTIONTYPE, 5);
        List<Suggestion> friendlist = SuggestionEngine.getInstance().provideSuggestions((MeetMeApp) getApplication(), savedInstanceState);
        for (Suggestion suggestion : friendlist) {
            friendTags.addTag(new TagListView.Tag(suggestion));
        }


    }

    public void doLaunchContactPicker(View view) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    // handle contact results
                    Uri contactData = data.getData();
                    //Cursor c = managedQuery(contactData, null, null, null, null);
                    Cursor contacts = this.getContentResolver().query(contactData, null, null, null, null);
                    int nameFieldColumnIndex = contacts.getColumnIndexOrThrow(ContactsContract.PhoneLookup.DISPLAY_NAME);
                    int numberFieldColumnIndex = contacts.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);

                    if (contacts.moveToFirst()) {
                        Suggestion contact = new SuggestionContact(contacts.getString(nameFieldColumnIndex), SuggestionTypes.PERSON, contacts.getString(numberFieldColumnIndex));
                        friendTags.addTag(new TagListView.Tag(contact));
                    }
                    break;
            }

        } else {
            // gracefully handle failure
            Log.w("debug", "Warning: activity result not ok");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void inviteFriends(View view) {
        // long remoteeventid = 13L;
        //TODO:  Toast.makeText(this, "No friends added Error here", Toast.LENGTH_LONG).show();
        List<TagListView.Tag> tags = sel_friendTags.getTags();
        MeetMeApp app = (MeetMeApp) getApplication();
        //TODO: If more than 2 Friends the user might want  to create a group
        //TODO: Add Group Tags
        // looks good, you might want to do it in a bulk job not one request for each invite
        // but one request containing a list of invites
        long remoteeventid = this.remoteEventId;
        System.out.println("EventID: " + Long.toString(remoteeventid));
        for (TagListView.Tag tag : tags) {
            if (tag.getObject().getType() == SuggestionTypes.PERSON) {
                SuggestionContact invite = (SuggestionContact) tag.getObject();
                final RemoteState state2 = new PostInviteUserState(app.getCtx(), remoteeventid, invite.getPhonenumber());
                state2.setCallback(new RemoteCallback() {
                    @Override
                    public void onRequestOk(RemoteResponse response) {
                        setResult(Activity.RESULT_OK);
                        finish();
                    }

                    @Override
                    public void onRequestFailed(RemoteResponse response) {
                        Toast.makeText(AddFriendsActivity.this, "Could not invite User: " + response.getString(), Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
                app.getCtx().invoke(state2);
            }
        }
    }


}
