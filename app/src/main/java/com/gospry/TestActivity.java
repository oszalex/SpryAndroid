package com.gospry;

import android.app.Activity;
import android.os.Bundle;
import android.test.ActivityUnitTestCase;
import android.view.View;
import android.widget.Button;

import com.gospry.suggestion.Suggestion;
import com.gospry.suggestion.SuggestionEngine;
import com.gospry.util.C;
import com.gospry.view.TagListView;
import com.shamanland.fab.FloatingActionButton;

import junit.framework.Test;

import java.util.List;

/**
 * Created by lexy on 08.04.15.
 */
public class TestActivity  extends Activity {

    TagListView suggestionTags;
    TagListView selectedTags;
    private TagListView.OnTagClickListener suggestionClickListener = new TagListView.OnTagClickListener() {
        @Override
        public void onTagClick(TagListView.Tag tag) {
            tag.setActivated(true);
            selectedTags.addTag(tag);
            //suggestionTags.removeTag(tag);
            suggestionTags.removeAllViews();
            Bundle bundle = new Bundle();
            bundle.putParcelable(C.EXTRA_LAST_ADDED, tag.getObject());
            //getLoaderManager().restartLoader(0, bundle, TestActivity.this);
        }
    };
    private TagListView.OnTagClickListener selectedClickListener = new TagListView.OnTagClickListener() {
        @Override
        public void onTagClick(TagListView.Tag tag) {
            tag.setActivated(false);
            selectedTags.removeTag(tag);
            Bundle bundle = new Bundle();
            bundle.putInt(C.SUGGESTIONTYPE, 1);
            List<Suggestion> list =  SuggestionEngine.getInstance().provideSuggestions((MeetMeApp) getApplication(), bundle);
            for (Suggestion suggestion : list) {
                suggestionTags.addTag(new TagListView.Tag(suggestion));
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event_test);
        selectedTags = (TagListView) findViewById(R.id.selected_dates);
        suggestionTags = (TagListView) findViewById(R.id.proposed_dates);
        suggestionTags.setListener(suggestionClickListener);
        selectedTags.setListener(selectedClickListener);
        Bundle bundle = new Bundle();
        bundle.putInt(C.SUGGESTIONTYPE, 1);
        List<Suggestion> list =  SuggestionEngine.getInstance().provideSuggestions((MeetMeApp) getApplication(), bundle);
        for (Suggestion suggestion : list) {
            suggestionTags.addTag(new TagListView.Tag(suggestion));
        }

    }

}
