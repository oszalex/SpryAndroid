package com.getbro.meetmeandroid;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.getbro.meetmeandroid.remote.RemoteCallback;
import com.getbro.meetmeandroid.remote.RemoteResponse;
import com.getbro.meetmeandroid.remote.RemoteState;
import com.getbro.meetmeandroid.remote.state.PostEventState;
import com.getbro.meetmeandroid.suggestion.Suggestion;
import com.getbro.meetmeandroid.suggestion.SuggestionEngine;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        selectedTags = (TagListView) findViewById(R.id.selected_list_view);
        suggestionTags = (TagListView) findViewById(R.id.proposed_list_view);

        suggestionTags.setListener(suggestionClickListener);
        selectedTags.setListener(selectedClickListener);

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
                return SuggestionEngine.getInstance().provideSuggestions((MeetMeApp) getApplication(), args);
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

    private TagListView.OnTagClickListener suggestionClickListener = new TagListView.OnTagClickListener() {
        @Override
        public void onTagClick(TagListView.Tag tag) {
            tag.setActivated(true);
            selectedTags.addTag(tag);
            Bundle bundle = new Bundle();
            bundle.putParcelable(C.EXTRA_LAST_ADDED, tag.getObject());
            getLoaderManager().restartLoader(0, bundle, NewEventActivity.this);
        }
    };

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

    public void createEvent(View view) {
        List<TagListView.Tag> tags = selectedTags.getTags();

        List<Suggestion> suggestions = new LinkedList<>();
        for (TagListView.Tag tag : tags) {
            suggestions.add(tag.getObject());
        }
        MeetMeApp app = (MeetMeApp) getApplication();
        RemoteState state = new PostEventState(app.getCtx(), suggestions);
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
        app.getCtx().invoke(state);
    }
}
