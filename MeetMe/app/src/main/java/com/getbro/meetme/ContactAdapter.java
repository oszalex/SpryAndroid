package com.getbro.meetme;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chris on 7/21/14.
 */
class ContactAdapter extends ArrayAdapter<ContactItem> {
    private static String TAG = ContactAdapter.class.getName();
    private LayoutInflater inflator = null;
    List pairList = null;
    private int layout;


    public ContactAdapter(Context context, int resource,
                          int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
        this.pairList = objects;
    }



    public void setInflater(LayoutInflater mInflater) {
        this.inflator = mInflater;
    }
    public void setLayout(int layout){
        this.layout = layout;
    }

    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        ViewHolder holder;
        try {
            if (convertView == null) {
                convertView = this.inflator.inflate(layout, null);
                holder = new ViewHolder();
                holder.key = (TextView) convertView.findViewById(R.id.key);
                holder.value = (TextView) convertView.findViewById(R.id.value);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            ContactItem pair = (ContactItem) getItem(position);
            String key = pair.mDisplayName;
            String value = pair.mPhone;

            holder.key.setText(key);
            holder.value.setText(value);
        } catch (Exception e) {
            Log.e(TAG, e.toString(), e);
        }
        return convertView;
    }


    static class ViewHolder {
        TextView key;
        TextView value;
    }
}