package com.getbro.bro;

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;
import com.getbro.bro.Json.Event;

public class EventsExpandableListAdapter extends BaseExpandableListAdapter {

    private final SparseArray<Event> events;
    public LayoutInflater inflater;
    public Activity activity;

    public EventsExpandableListAdapter(Activity act, Event[] eventArray) {

        activity = act;
        this.events = new SparseArray<Event>();
        inflater = act.getLayoutInflater();

        for (int i = 0; i < eventArray.length; i++)
            events.append(i,eventArray[i]);
    }

    @Override
    public long getChildId(int eventPosition, int childPosition) {
        return 0;
    }

    //Maps und sonstigen Text hier setzen
    @Override
    public View getChildView(int eventPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        Event event = (Event)getGroup(eventPosition);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.listrow_details, null);

        TextView mPublic = (TextView) convertView.findViewById(R.id.textView1);

        mPublic.setText(String.valueOf(event.IsPublic));

        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, null,
                        Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }



    @Override
    public int getChildrenCount(int eventPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int eventPosition) {
        return events.get(eventPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public void onGroupCollapsed(int eventPosition) {
        super.onGroupCollapsed(eventPosition);
    }

    @Override
    public void onGroupExpanded(int eventPosition) {
        super.onGroupExpanded(eventPosition);
    }

    @Override
    public int getGroupCount() {
        return events.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int eventPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_group, null);
        }
        Event event = (Event)getGroup(eventPosition);
        ((CheckedTextView) convertView).setText(event.Name);
        ((CheckedTextView) convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int eventPosition, int childPosition) {
        return false;
    }
}