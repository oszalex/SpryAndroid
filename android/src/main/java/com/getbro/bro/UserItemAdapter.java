package com.getbro.bro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.getbro.bro.Json.User;

import java.util.ArrayList;

/**
 * Created by chris on 03/05/14.
 */
public class UserItemAdapter extends ArrayAdapter<User> {
    private ArrayList<User> users;

    public UserItemAdapter(Context context, int textViewResourceId, ArrayList<User> users) {
        super(context, textViewResourceId, users);
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.profillistitem, null);
        }

        User user = users.get(position);
        if (user != null) {
            TextView username = (TextView) v.findViewById(R.id.username);

            if (username != null) {
                username.setText(user.UserName);
            }
        }
        return v;
    }
}