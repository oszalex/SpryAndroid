package com.gospry;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gospry.generate.Event;
import com.gospry.remote.RemoteCallback;
import com.gospry.remote.RemoteResponse;
import com.gospry.remote.RemoteState;
import com.gospry.remote.state.PostEventState;
import com.gospry.remote.state.PostInviteUserState;
import com.gospry.suggestion.Suggestion;
import com.gospry.suggestion.SuggestionContact;
import com.gospry.suggestion.SuggestionEngine;
import com.gospry.suggestion.SuggestionTypes;
import com.gospry.util.C;
import com.gospry.util.Response;
import com.gospry.view.TagListView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by rich on 21.11.14.
 */
public class NewEventActivity extends Activity implements LoaderManager.LoaderCallbacks<List<Suggestion>> {

    final Response outresponse = new Response();
    TagListView selectedTags;
    TagListView suggestionTags;
    int suggestionorder;
    private TagListView.OnTagClickListener suggestionClickListener = new TagListView.OnTagClickListener() {
        @Override
        public void onTagClick(TagListView.Tag tag) {
            tag.setActivated(true);
            selectedTags.addTag(tag);
            Bundle bundle = new Bundle();
            bundle.putParcelable(C.EXTRA_LAST_ADDED, tag.getObject());
            newevent.set(tag.getObject());
            //Automatically create Event after Location/Date/Time and tag is Set
            if (suggestionorder == 5) {
                createEvent(null);
                Button neweventbutton = (Button) findViewById(R.id.neweventbutton);
                neweventbutton.setVisibility(View.VISIBLE);
            }
            getLoaderManager().restartLoader(0, bundle, NewEventActivity.this);
        }
    };
    Event newevent;
    //TODO: Das muss angepasst werden, damit die Reihenfolge etc stimmt,  vl nur return button enablen?
    // you can do this more easily in the SuggestionEngine. there you simply sort the tags (i put example there)
    private TagListView.OnTagClickListener selectedClickListener = new TagListView.OnTagClickListener() {
        @Override
        public void onTagClick(TagListView.Tag tag) {
            tag.setActivated(false);
            selectedTags.removeTag(tag);
            Bundle bundle = new Bundle();
            bundle.putParcelable(C.EXTRA_LAST_REMOVED, tag.getObject());
            getLoaderManager().restartLoader(0, bundle, NewEventActivity.this);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        //TODO: floating Button?!

        selectedTags = (TagListView) findViewById(R.id.selected_list_view);
        suggestionTags = (TagListView) findViewById(R.id.proposed_list_view);

        suggestionTags.setListener(suggestionClickListener);
        selectedTags.setListener(selectedClickListener);

        Button neweventbutton = (Button) findViewById(R.id.neweventbutton);
        neweventbutton.setVisibility(View.INVISIBLE);

        suggestionorder = 1;
        newevent = new Event();
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public Loader<List<Suggestion>> onCreateLoader(int id, final Bundle args) {
        AsyncTaskLoader<List<Suggestion>> loader = new AsyncTaskLoader<List<Suggestion>>(this) {
            @Override
            public List<Suggestion> loadInBackground() {
                Bundle bundle = new Bundle();
                bundle.putInt(C.SUGGESTIONTYPE, suggestionorder);
                //TODO: wenn date=today dann in30min etc anzeigen sonst nicht
                //TODO: bei date = now keine zeiten anzeigen
                //TODO: customDates/Tags, wie werden gespeichert
                suggestionorder++;
                return SuggestionEngine.getInstance().provideSuggestions((MeetMeApp) getApplication(), bundle);
            }

            @Override
            protected void onStartLoading() {
                forceLoad();
            }
        };
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<Suggestion>> loader, List<Suggestion> data) {
        suggestionTags.clearTags();
        for (Suggestion suggestion : data) {
            suggestionTags.addTag(new TagListView.Tag(suggestion));
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }

    public void createEvent(View view) {
        List<TagListView.Tag> tags = selectedTags.getTags();
        //TODO: Tags werden im json geaddet, der rest schon vorher in eventstruktur abgelegt
        List<Suggestion> suggestions = new LinkedList<>();
        for (TagListView.Tag tag : tags) {
            if (tag.getObject().getType() == SuggestionTypes.TAG) {
                suggestions.add(tag.getObject());
            }
        }
        MeetMeApp app = (MeetMeApp) getApplication();
        RemoteState state = new PostEventState(app.getCtx(), suggestions, newevent);
        RemoteResponse x;
        state.setCallback(new RemoteCallback() {
            @Override
            public void onRequestOk(RemoteResponse response) {
                setResult(Activity.RESULT_OK);
                Toast.makeText(NewEventActivity.this, "Event created: " + response.getString() + "Activity: " + this.toString(), Toast.LENGTH_LONG).show();
                //gets response out of here
                outresponse.setResponse(response.getJsonObject());
            }

            @Override
            public void onRequestFailed(RemoteResponse response) {
                Toast.makeText(NewEventActivity.this, "Could not create event: " + response.getString(), Toast.LENGTH_LONG).show();
            }
        });
        app.getCtx().invoke(state);


    }

    public void inviteFriends(View view) {
        // long remoteeventid = 13L;
        List<TagListView.Tag> tags = selectedTags.getTags();
        MeetMeApp app = (MeetMeApp) getApplication();
        // looks good, you might want to do it in a bulk job not one request for each invite
        // but one request containing a list of invites
        long remoteeventid = outresponse.getResponse().get("id").getAsInt();
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
                        Toast.makeText(NewEventActivity.this, "Could not invite User: " + response.getString(), Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
                app.getCtx().invoke(state2);
            }
        }
    }


}
