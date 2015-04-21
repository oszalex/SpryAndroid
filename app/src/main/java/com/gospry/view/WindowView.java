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
    private TagListView tags;
    private int type;


    public WindowView(Context context, int type) {
        super(context);
        this.type= type;
        this.context = context;


        LayoutInflater inflater;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.window_layout, this);

        textViewHeadline = (TextView) mView.findViewById(R.id.textViewHeadline);
        tags = (TagListView) mView.findViewById(R.id.proposed_list_view2);

        Bundle bundle = new Bundle();
        bundle.putInt(C.SUGGESTIONTYPE, type);
        List<Suggestion> suggestions =  SuggestionEngine.getInstance().provideSuggestions((MeetMeApp) context.getApplicationContext(), bundle);
        for (Suggestion suggestion : suggestions) {
            tags.addTag(new TagListView.Tag(suggestion));
        }

        textViewHeadline.setText("Überschrift!!");
    }

}