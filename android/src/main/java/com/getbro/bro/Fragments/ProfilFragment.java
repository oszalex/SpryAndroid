package com.getbro.bro.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.getbro.bro.Data.User;
import com.getbro.bro.R;
import com.getbro.bro.UIComponents.CustomTextView;

/**
 * Created by chris on 03/05/14.
 */
public class ProfilFragment extends Fragment {
    private User user;

    public ProfilFragment(User u){
        this.user = u;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.userprofile, container, false);

        //populate view
        CustomTextView full_name = (CustomTextView) v.findViewById(R.id.profil_fullname);
        CustomTextView profil_follower = (CustomTextView) v.findViewById(R.id.profil_follower);
        CustomTextView profil_followed = (CustomTextView) v.findViewById(R.id.profil_follow);
        TextView profil_username = (TextView) v.findViewById(R.id.profil_username);
        TextView profil_sex = (TextView) v.findViewById(R.id.profil_sex);


        full_name.setText(user.UserName);
        profil_username.setText(user.UserName);
        profil_sex.setText(user.Sex);
        profil_followed.setText(String.valueOf(user.numOfFollowed()));
        profil_follower.setText(String.valueOf(user.numOfFollowers()));

        return v;
    }
}