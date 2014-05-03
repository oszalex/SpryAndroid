package com.getbro.bro.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.getbro.bro.Json.User;
import com.getbro.bro.R;
import com.getbro.bro.UserItemAdapter;

import java.util.ArrayList;

/**
 * Created by chris on 03/05/14.
 */
public class FriendListFragment extends Fragment {
    private ArrayList<User> users;

    public FriendListFragment(ArrayList<User> users){
        this.users = users;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_friendlist, container, false);

        ListView listView = (ListView) v.findViewById(R.id.ListViewId);
        listView.setAdapter(new UserItemAdapter(this.getActivity(), android.R.layout.simple_list_item_1, users));

        return v;

    }
}