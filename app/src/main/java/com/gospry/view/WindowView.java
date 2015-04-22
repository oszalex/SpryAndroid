package com.gospry.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gospry.MeetMeApp;
import com.gospry.R;
import com.gospry.suggestion.Suggestion;
import com.gospry.suggestion.SuggestionEngine;
import com.gospry.util.C;

import java.util.List;

/**
 * Created by cs on 21.04.2015.
 */
public class WindowView extends LinearLayout {

    private View mView;
    private Context context;
    private TextView textViewHeadline;
    private TagListView tags,sel_tags;
    private int type;


    public WindowView(Context context, int type) {
        super(context);
        this.type= type;
        this.context = context;


        LayoutInflater inflater;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.new_event_test, this);

        textViewHeadline = (TextView) mView.findViewById(R.id.textViewHeadline);
        tags = (TagListView) mView.findViewById(R.id.proposed_list_view);
        sel_tags = (TagListView) mView.findViewById(R.id.selected_list_view);
        tags.setListener(suggestionClickListener);
        sel_tags.setListener(selectedClickListener);
        Bundle bundle = new Bundle();
        bundle.putInt(C.SUGGESTIONTYPE, type);
        List<Suggestion> suggestions =  SuggestionEngine.getInstance().provideSuggestions((MeetMeApp) context.getApplicationContext(), bundle);
        for (Suggestion suggestion : suggestions) {
            tags.addTag(new TagListView.Tag(suggestion));
        }

        textViewHeadline.setText(suggestions.get(1).getType().toString());
    }
    private TagListView.OnTagClickListener suggestionClickListener = new TagListView.OnTagClickListener() {
        @Override
        public void onTagClick(TagListView.Tag tag) {
            tag.setActivated(true);
            sel_tags.addTag(tag);
            //suggestionTags.removeTag(tag);
            tags.removeAllViews();
            Bundle bundle = new Bundle();
            bundle.putParcelable(C.EXTRA_LAST_ADDED, tag.getObject());
            //getLoaderManager().restartLoader(0, bundle, TestActivity.this);
        }
    };
    private TagListView.OnTagClickListener selectedClickListener = new TagListView.OnTagClickListener() {
        @Override
        public void onTagClick(TagListView.Tag tag) {
            tag.setActivated(false);
            sel_tags.removeTag(tag);
            Bundle bundle = new Bundle();
            //TODO: Get the right Number here from the Tag that has been deleted
            bundle.putInt(C.SUGGESTIONTYPE, 1);
            List<Suggestion> list =  SuggestionEngine.getInstance().provideSuggestions((MeetMeApp) context.getApplicationContext(), bundle);
            for (Suggestion suggestion : list) {
                tags.addTag(new TagListView.Tag(suggestion));
            }
        }
    };

}