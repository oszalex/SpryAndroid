package com.getbro.meetmeandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.getbro.meetmeandroid.generate.Event;
import com.getbro.meetmeandroid.generate.Keyword;
import com.getbro.meetmeandroid.generate.LocalSession;
import com.getbro.meetmeandroid.remote.RemoteCallback;
import com.getbro.meetmeandroid.remote.RemoteResponse;
import com.getbro.meetmeandroid.remote.state.GetEventState;
import com.getbro.meetmeandroid.suggestion.Suggestion;
import com.getbro.meetmeandroid.suggestion.SuggestionTypes;
import com.getbro.meetmeandroid.util.C;
import com.getbro.meetmeandroid.view.TagListView;

import java.util.List;


public class EventDetailActivity extends Activity {

    private AppCtx context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        MeetMeApp app = (MeetMeApp) getApplication();
        context = app.getCtx();

        Long eventId = getIntent().getLongExtra(C.EXTRA_EVENT_ID, -1);
        if (eventId == -1) {
            throw new IllegalStateException("detail view did not get an event id");
        }

        Event event = app.getSession().findEvent(eventId);

        ViewHolder holder = new ViewHolder(findViewById(R.id.background));
        holder.update(event, app.getSession());

        GetEventState state = new GetEventState(context, eventId);
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    private class ViewHolder {

        private final View view;
        private final TextView desc;
        private final TagListView keywords;

        public ViewHolder(View view) {
            this.view = view;
            this.desc = (TextView) view.findViewById(R.id.desc);
            this.keywords = (TagListView) view.findViewById(R.id.keywords);
        }

        public void update(Event event, LocalSession session) {
            desc.setText(event.getDescription());
            List<Keyword> kws = event.loadKeywords(session).all();
            for (Keyword word : kws) {
                keywords.addTag(new TagListView.Tag(new Suggestion(word.getText(), SuggestionTypes.of(word.getText()))));
            }
        }
    }
}
