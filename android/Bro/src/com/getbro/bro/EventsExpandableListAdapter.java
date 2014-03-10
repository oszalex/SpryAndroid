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

public class EventsExpandableListAdapter extends BaseExpandableListAdapter {

  private final SparseArray<Event> events;
  public LayoutInflater inflater;
  public Activity activity;

  public EventsExpandableListAdapter(Activity act, SparseArray<Event> events) {
    activity = act;
    this.events = events;
    inflater = act.getLayoutInflater();
  }

  @Override
  public Object getChild(int eventPosition, int childPosition) {
    return events.get(eventPosition).children.get(childPosition);
  }

  @Override
  public long getChildId(int eventPosition, int childPosition) {
    return 0;
  }

  //Maps und sonstigen Text hier setzen
  @Override
  public View getChildView(int eventPosition, final int childPosition,
      boolean isLastChild, View convertView, ViewGroup parent) {
    final String children = (String) getChild(eventPosition, childPosition);
    TextView text = null;
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.listrow_details, null);
    }
    text = (TextView) convertView.findViewById(R.id.textView1);
    text.setText(children);
    convertView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(activity, children,
            Toast.LENGTH_SHORT).show();
      }
    });
    return convertView;
  }

  @Override
  public int getChildrenCount(int eventPosition) {
    return events.get(eventPosition).children.size();
  }

  @Override
  public Object getGroup(int eventPosition) {
    return events.get(eventPosition);
  }

  @Override
  public int getGroupCount() {
    return events.size();
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
  public long getGroupId(int eventPosition) {
    return 0;
  }

  @Override
  public View getGroupView(int eventPosition, boolean isExpanded,
      View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.listrow_group, null);
    }
    Event group = (Event) getGroup(eventPosition);
    ((CheckedTextView) convertView).setText(group.string);
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