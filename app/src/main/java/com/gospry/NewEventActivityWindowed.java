package com.gospry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gospry.generate.Event;
import com.gospry.remote.RemoteCallback;
import com.gospry.remote.RemoteResponse;
import com.gospry.remote.RemoteState;
import com.gospry.remote.state.PostEventState;
import com.gospry.suggestion.Suggestion;
import com.gospry.suggestion.SuggestionTypes;
import com.gospry.util.Response;
import com.gospry.view.TagListView;
import com.gospry.view.WindowViewList;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by cs on 21.04.2015.
 */

public class NewEventActivityWindowed extends Activity {

    final Response outresponse = new Response();
    View mView;
    Event newevent;
    Object lock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_windowed);
        WindowViewList windowViewList = (WindowViewList) findViewById(R.id.windowViewList);
        WindowViewList selwindowViewList = (WindowViewList) findViewById(R.id.selwindowViewList);
        newevent = new Event();
        windowViewList.init(newevent);


    }

    public void addEvent(View view) {
        createEvent(view);
    }

    private void startNext() {
        Intent intent = new Intent();
        intent.setClass(NewEventActivityWindowed.this, AddFriendsActivity.class);
        intent.putExtra("RemoteEventId", outresponse.getResponse().get("id").getAsLong());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MeetMeApp app = (MeetMeApp) getApplication();
        app.startActivity(intent);
        finish();
    }

    public void createEvent(View view) {

        List<TagListView.Tag> tags = new LinkedList<TagListView.Tag>();
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
                Toast.makeText(NewEventActivityWindowed.this, "Event created: " + response.getString() + "Activity: " + this.toString(), Toast.LENGTH_LONG).show();
                //gets response out of here
                outresponse.setResponse(response.getJsonObject());
                startNext();
                finish();
            }

            @Override
            public void onRequestFailed(RemoteResponse response) {
                Toast.makeText(NewEventActivityWindowed.this, "Could not create event: " + response.getString(), Toast.LENGTH_LONG).show();
                finish();
            }
        });
        app.getCtx().invoke(state);

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