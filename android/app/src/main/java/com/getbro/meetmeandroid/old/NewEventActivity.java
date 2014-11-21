package com.getbro.meetmeandroid.old;

import android.app.Activity;

public class NewEventActivity extends Activity {
    private final String TAG = Activity.class.toString();

    /*
    //private SuggestionAdapter m_adapter;
    private EditText text;
    private ArrayList<String> tags = new ArrayList<String>();

    private int state = 0;

    private void addLabel(Suggestion suggestion){
        final LabelLayout auswahlLayout = (LabelLayout)findViewById(R.id.auswahl);

        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.suggestion_element, null);

        // load children and setup properties
        TextView tv = (TextView) customView.findViewById(R.id.suggestion);
        tv.setText(suggestion.getValue());
        tv.setBackgroundDrawable(getResources().getDrawable(suggestion.getDrawableId()));
        // add it to the layout
        auswahlLayout.addView(customView);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_idea_event);

        drawSuggestions(getSuggestions(new ArrayList<Suggestion>()));
    }


    public void drawSuggestions(ArrayList<Suggestion> suggestions){
        final LabelLayout taggedLayout = (LabelLayout)findViewById(R.id.tagged_layout);

        // remove all if there are any. might not be useful here!
        taggedLayout.removeAllViews();

        // your data. not necessarily strings...
        LayoutInflater inflater = LayoutInflater.from(this);
        for (Suggestion sug : suggestions) {
            // this way you can create custom view
            View customView = inflater.inflate(R.layout.suggestion_element, null);
            // setup the listeners
            setupListeners(customView);

            // load children and setup properties
            TextView tv = (TextView) customView.findViewById(R.id.suggestion);
            tv.setText(sug.getValue());
            tv.setBackground(getResources().getDrawable(sug.getDrawableId()));
            // add it to the layout
            taggedLayout.addView(customView);

            //attach object
            tv.setTag(sug);

            // must set the margin manually! the one of the xml in the root not is not considered!
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) customView.getLayoutParams(); float dpi = getResources().getDisplayMetrics().density;
            lp.bottomMargin = lp.topMargin = lp.leftMargin = lp.rightMargin = (int)(5 * dpi);
        }
    }


     * helper method to set label button click listener
     *
     * @param view
    private void setupListeners(final View view) {
    private void setupListeners(final View view) {
        final LabelLayout taggedLayout = (LabelLayout)findViewById(R.id.tagged_layout);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                taggedLayout.removeView(view);
                return true;
            }
        });
        view.setOnClickListener(new View.OnClickListener() {

            //boolean green = false;

            @Override
            public void onClick(View v) {
                taggedLayout.removeView(view);

                TextView tv  = (TextView) view.findViewById(R.id.suggestion);

                addLabel(Suggestion.fromView(tv));
                tags.add(tv.getText().toString());

                TextView tv  = (TextView) view.findViewById(R.id.suggestion);

                if (green) {
                    //view.setBackgroundColor(0xff449933);
                    tv.setBackground(getResources().getDrawable(R.drawable.tag_label));
                } else {
                    //view.setBackgroundColor(0xff994433);
                    tv.setBackground(getResources().getDrawable(R.drawable.tag_label));
                }
                green = !green;
            }
        });
    }




    private ArrayList<Suggestion> getSuggestions(ArrayList<Suggestion> context){
        ArrayList<Suggestion> s = new ArrayList<Suggestion>();

        //add datetime
        s.add(new Suggestion("tomorrow", SuggestionTypes.DATETIME));
        s.add(new Suggestion("now", SuggestionTypes.DATETIME));
        s.add(new Suggestion("monday", SuggestionTypes.DATETIME));
        s.add(new Suggestion("tuesday", SuggestionTypes.DATETIME));
        s.add(new Suggestion("after work", SuggestionTypes.DATETIME));
        s.add(new Suggestion("today", SuggestionTypes.DATETIME));
        s.add(new Suggestion("never", SuggestionTypes.DATETIME));

        //add friends
        s.addAll(getContactSuggestions(getApplicationContext()).subList(0, 3) );

        //add tags
        s.add(new Suggestion("#sometag", SuggestionTypes.TAG));
        s.add(new Suggestion("#hash", SuggestionTypes.TAG));
        s.add(new Suggestion("#nice", SuggestionTypes.TAG));

        return s;
    }

    private void setupSuggest(){
        int resource;
        final GridView grid = (GridView) findViewById(R.id.suggestionGrid);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
            Object o = grid.getItemAtPosition(position);

            TextView label = (TextView)arg1.findViewById(R.id.suggestion);

            String suggestion = label.getText().toString();

            //remove suggestion from list
            //label.setVisibility(View.GONE);

            //add suggestion to text
                Editable old_str = text.getText();
            SpannableString span = new SpannableString(old_str + " " + suggestion);

            //restore old spans

            BackgroundColorSpan[] old_spans = old_str.getSpans(0, old_str.length(), BackgroundColorSpan.class);

                for(BackgroundColorSpan sp : old_spans)
                    span.setSpan(sp, old_str.getSpanStart(sp), old_str.getSpanEnd(sp), 0);


            //add new span

            span.setSpan(
                    new BackgroundColorSpan(Color.GRAY),
                    text.getText().length() + 1,
                    text.getText().length() + suggestion.length() + 1,
                    0);

            text.setText(span);

            //set cursor to the end
            text.setSelection(text.getText().length());

            updateSuggestions();
            }
        });

        m_adapter = new SuggestionAdapter(NewEventActivity.this, R.layout.suggestion_element, s);
        updateSuggestions();
        grid.setAdapter(m_adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void finish(View v){
        finish();
    }



    public void createEvent(View v){
        final LabelLayout auswahlLayout = (LabelLayout)findViewById(R.id.auswahl);

        String rawtext = Suggestion.listToString(Suggestion.fromView(auswahlLayout));



        Log.v(TAG, "send event:" + rawtext);

        //MeetMeAPI.createEvent(rawEvent.getText().toString());
        API.createEvent(this, rawtext).setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                finish();
            }
        });
    }


    private static ArrayList<Suggestion> getContactSuggestions(Context ctx){
        String contactName = null;
        ArrayList<Suggestion> contacts = new ArrayList<Suggestion>();
        Cursor cursor = ctx.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()){
            contactName  = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            contacts.add(new Suggestion("+" + contactName, SuggestionTypes.PERSON));
        }

        return contacts;
    }
    */
}
