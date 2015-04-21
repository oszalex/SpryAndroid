package com.gospry;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.gospry.view.WindowView;
import com.gospry.view.WindowViewList;
import com.shamanland.fab.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by cs on 21.04.2015.
 */

public class NewEventActivityWindowed extends Activity  {

    View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_windowed);
        WindowViewList windowViewList = (WindowViewList) findViewById(R.id.windowViewList);
        windowViewList.init();
    }
}


/*
        final LinearLayout content = (LinearLayout) findViewById(R.id.linLayoutContent1);

        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewEventActivityWindowed.this, "jojo", Toast.LENGTH_SHORT).show();
                Log.v("XX",""+"jojjo");
                ValueAnimator va = ValueAnimator.ofInt(content.getHeight(), 0);
                va.setDuration(700);
                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Integer value = (Integer) animation.getAnimatedValue();
                        Log.v("XX",""+value);
                        content.getLayoutParams().height = value.intValue();
                        content.requestLayout();
                    }
                });
                va.start();
            }
        });
 */