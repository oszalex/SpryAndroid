package com.getbro.bro;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.widget.MultiAutoCompleteTextView;
import com.getbro.bro.EventParser.TokenWebserviceResource;
import com.getbro.bro.Webservice.HttpGetRequest;

public class NewEventActivity extends Activity {

    final Context context = this;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newevent);

        final MultiAutoCompleteTextView textView = (MultiAutoCompleteTextView)findViewById(R.id.text);

        AutoCompleteAdapter adapter = new AutoCompleteAdapter(context,android.R.layout.simple_list_item_1,new TokenWebserviceResource(getResources().getString(R.string.webService),"raphi","password"));
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
    }
}