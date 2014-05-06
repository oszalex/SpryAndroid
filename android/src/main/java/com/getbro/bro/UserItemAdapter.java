package com.getbro.bro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.getbro.bro.Json.User;
import com.getbro.bro.Model.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 03/05/14.
 */
public class UserItemAdapter extends ArrayAdapter<UserModel> {
    private List<UserModel> users;

    public UserItemAdapter(Context context, int textViewResourceId, List<UserModel> users) {
        super(context, textViewResourceId, users);
        this.users = users;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.profillistitem, null);
        }

        UserModel user = users.get(position);
        if (user != null) {
            TextView username = (TextView) v.findViewById(R.id.username);

            if (username != null) {
                username.setText(user.UserName);
            }

            v.setTag(R.id.list_item, user);
        }
        return v;
    }
}