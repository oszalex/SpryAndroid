package com.gospry.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gospry.R;
import com.gospry.generate.Event;
import com.gospry.suggestion.Suggestion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by cs on 21.04.2015.
 */
public class WindowViewList extends LinearLayout {

    private List<WindowView> windowViewList = new ArrayList<>();
    private List<View> convertViewList = new LinkedList<>();
    private Context context;
    protected Event event;


    public void init(Event newevent)
    {
        this.event= newevent;
        for(int i=1;i<5;i++){
            WindowView date = new WindowView(context,i,this);
            //windowViewList.add(date);
            addView(date);
        }
    }

    public WindowViewList(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }
    public void setEventDetail(TagListView.Tag detail){
        event.set(detail.getObject());
    }

}
