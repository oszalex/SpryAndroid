package com.getbro.bro.Fragments;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.getbro.bro.R;

/**
 * Created by chris on 03/05/14.
 */
public class ProfilFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_profil, container, false);

        TextView tv = (TextView) v.findViewById(R.id.profil_fullname);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "MavenProLight.ttf");

        tv.setTypeface(font);

        return v;

    }
}