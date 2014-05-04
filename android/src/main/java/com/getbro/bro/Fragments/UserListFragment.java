package com.getbro.bro.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.getbro.bro.Json.User;
import com.getbro.bro.R;
import com.getbro.bro.UserItemAdapter;

import java.util.ArrayList;

/**
 * Created by chris on 03/05/14.
 */
public class UserListFragment extends Fragment {
    private final String TAG = UserListFragment.class.getSimpleName();
    private ArrayList<User> users;

    public UserListFragment(ArrayList<User> users){
        this.users = users;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.userlist, container, false);

        ListView listView = (ListView) v.findViewById(R.id.UserList);
        listView.setAdapter(new UserItemAdapter(this.getActivity(), android.R.layout.simple_list_item_1, users));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Log.d(TAG, "received user selection: " + arg1.toString() + " " + position);
                User u = (User)arg1.getTag(R.id.list_item);

                Log.d(TAG, "user: " + u.toString());

                //load new ProfileFragment

            }
        });

        return v;

    }
}