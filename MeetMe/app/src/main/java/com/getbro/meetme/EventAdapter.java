package com.getbro.meetme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private EventItem[] itemsData;
    private Context context;

    public EventAdapter(Context context, EventItem[] itemsData) {
        this.itemsData = itemsData;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData

        viewHolder.txtViewTitle.setText(itemsData[position].Name);
        viewHolder.txtViewTags.setText(TextUtils.join(", ", itemsData[position].Tags));
        viewHolder.txtViewTimeLeft.setText(DateUtils.getRelativeTimeSpanString(itemsData[position].DateTime.getTime(), (new Date()).getTime(), 0L));
        //viewHolder.imgViewIcon.setImageResource(itemsData[position].getImageUrl());

    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.length;
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewTitle;
        public TextView txtViewTags;
        public TextView txtViewTimeLeft;
        //public ImageView imgViewIcon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.eventname);
            txtViewTags = (TextView) itemLayoutView.findViewById(R.id.tags);
            txtViewTimeLeft = (TextView) itemLayoutView.findViewById(R.id.timeleft);
        }
    }
}