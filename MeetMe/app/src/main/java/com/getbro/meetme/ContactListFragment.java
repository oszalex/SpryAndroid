package com.getbro.meetme;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.getbro.meetme.EventAdapter;
import com.getbro.meetme.EventItem;
import com.getbro.meetme.R;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ContactListFragment extends ListFragment {
    private List contactItemList = new LinkedList<ContactItem>();
    private LayoutInflater mInflater;
    public boolean taskRun = false;
    long currentID = 0;
    long currentContactID = 0;
    ContactAdapter mAdapter;

    public ContactListFragment(){}

    public void setDataList( List list) {
        Activity act = getActivity();
        this.contactItemList = list;
        if(act != null) {
            mAdapter = new ContactAdapter(act, R.layout.friend_list_item,R.id.key, list);
            mAdapter .setInflater(mInflater);
            mAdapter.setLayout(R.layout.friend_list_item);
            setListAdapter(mAdapter );
            getListView().invalidate();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mInflater = (LayoutInflater) getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(!taskRun){
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ListContactTask task= new ListContactTask(getActivity(),ft);
            task.execute();
        }
        taskRun = true;
        mAdapter = new ContactAdapter(getActivity(), R.layout.friend_list_item,R.id.key, contactItemList);
        mAdapter .setInflater(mInflater);
        mAdapter.setLayout(R.layout.friend_list_item);
        setListAdapter(mAdapter );
        ListView listView = getListView();
        getListView().invalidate();
    }
}
