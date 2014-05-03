package com.getbro.bro.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.MultiAutoCompleteTextView;
import com.getbro.bro.AutoCompleteAdapter;
import com.getbro.bro.EventParser.TokenWebserviceResource;
import com.getbro.bro.R;
import com.getbro.bro.Webservice.HttpGetRequest;
import com.getbro.bro.WhitespaceTokenizer;
import org.apache.http.client.methods.HttpGet;

/**
 * Created by chris on 03/05/14.
 */
public class NewEventFragment extends Fragment {

    private HttpGetRequest httpRequest;

    public NewEventFragment(HttpGetRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_newevent, container, false);

        final MultiAutoCompleteTextView textView = (MultiAutoCompleteTextView)v.findViewById(R.id.text);

        AutoCompleteAdapter adapter = new AutoCompleteAdapter(v.getContext(),android.R.layout.simple_list_item_1,new TokenWebserviceResource(httpRequest));
        textView.setAdapter(adapter);
        textView.setTokenizer(new WhitespaceTokenizer());

        textView.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Layout layout = textView.getLayout();
                int pos = textView.getSelectionStart();
                int line = layout.getLineForOffset(pos);
                int baseline = layout.getLineBaseline(line);
                int bottom = textView.getHeight();

                textView.setDropDownVerticalOffset(baseline-bottom);

            }
        });

        return v;
    }
}