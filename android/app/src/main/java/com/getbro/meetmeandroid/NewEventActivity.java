package com.getbro.meetmeandroid;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.getbro.meetmeandroid.generate.Event;
import com.getbro.meetmeandroid.remote.RemoteCallback;
import com.getbro.meetmeandroid.remote.RemoteResponse;
import com.getbro.meetmeandroid.remote.RemoteState;
import com.getbro.meetmeandroid.remote.state.PostEventState;
import com.getbro.meetmeandroid.remote.state.PostInviteUserState;
import com.getbro.meetmeandroid.suggestion.Suggestion;
import com.getbro.meetmeandroid.suggestion.SuggestionContact;
import com.getbro.meetmeandroid.suggestion.SuggestionEngine;
import com.getbro.meetmeandroid.suggestion.SuggestionTypes;
import com.getbro.meetmeandroid.util.C;
import com.getbro.meetmeandroid.view.TagListView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by rich on 21.11.14.
 */
public class NewEventActivity extends Activity implements LoaderManager.LoaderCallbacks<List<Suggestion>> {

    TagListView selectedTags;
    TagListView suggestionTags;

    int suggestionorder;
    Event newevent;
    private TagListView.OnTagClickListener suggestionClickListener = new TagListView.OnTagClickListener() {
        @Override
        public void onTagClick(TagListView.Tag tag) {
            tag.setActivated(true);
            selectedTags.addTag(tag);
            Bundle bundle = new Bundle();
            bundle.putParcelable(C.EXTRA_LAST_ADDED, tag.getObject());
            newevent.set(tag.getObject());
            getLoaderManager().restartLoader(0, bundle, NewEventActivity.this);
        }
    };
    //TODO: Das muss angepasst werden, damit die Reihenfolge etc stimmt,  vl nur return button enablen?
    private TagListView.OnTagClickListener selectedClickListener = new TagListView.OnTagClickListener() {
        @Override
        public void onTagClick(TagListView.Tag tag) {
            tag.setActivated(false);
            selectedTags.removeTag(tag);
            Bundle bundle = new Bundle();
            bundle.putParcelable(C.EXTRA_LAST_REMOVED, tag.getObject());
            //TODO: Automatically create Event after 5 clicks? or let user decide?
            //   if(suggestionorder < 5) createEvent(View view);
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
        //TODO: Tags werden in im json geaddet, der rest schon vorher in eventstruktur abgelegt
        List<Suggestion> suggestions = new LinkedList<>();
        for (TagListView.Tag tag : tags) {
            if (tag.getObject().getType() == SuggestionTypes.TAG) {
                suggestions.add(tag.getObject());
            }
        }
        MeetMeApp app = (MeetMeApp) getApplication();
        //TODO: This Request must return the remote id of the event so that the user can be invited
        long remoteeventid = 1L;
        RemoteState state = new PostEventState(app.getCtx(), suggestions, newevent);
        state.setCallback(new RemoteCallback() {
            @Override
            public void onRequestOk(RemoteResponse response) {
                setResult(Activity.RESULT_OK);
                finish();
            }

            @Override
            public void onRequestFailed(RemoteResponse response) {
                Toast.makeText(NewEventActivity.this, "Could not create event: " + response.getString(), Toast.LENGTH_LONG).show();
            }
        });
        //TODO:Fix this on serverside and test here
        for (TagListView.Tag tag : tags) {
            if (tag.getObject().getType() == SuggestionTypes.PERSON) {
                SuggestionContact invite = (SuggestionContact) tag.getObject();
                state = new PostInviteUserState(app.getCtx(), invite.getPhonenumber(), remoteeventid);
                state.setCallback(new RemoteCallback() {
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
            }
        }
        app.getCtx().invoke(state);
    }

}
