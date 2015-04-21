package com.gospry.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gospry.R;
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


    public void init()
    {
        for(int i=1;i<5;i++){
            WindowView date = new WindowView(context,i);
            //windowViewList.add(date);
            addView(date);
        }
    }

    public WindowViewList(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

}
