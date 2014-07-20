package com.getbro.meetme;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private EventItem[] itemsData;

    public EventAdapter(EventItem[] itemsData) {
        this.itemsData = itemsData;
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
        viewHolder.txtViewTags.setText(itemsData[position].Tags.toString());
        viewHolder.txtViewTimeLeft.setText(itemsData[position].DateTime.toString());
        //viewHolder.imgViewIcon.setImageResource(itemsData[position].getImageUrl());

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





    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.length;
    }
}