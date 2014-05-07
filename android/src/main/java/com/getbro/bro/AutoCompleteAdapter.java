package com.getbro.bro;

import android.content.Context;
import android.widget.*;
import com.getbro.bro.EventParser.BroToken;
import com.getbro.bro.EventParser.ITokenResource;

import java.util.*;

public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

    private BroToken broToken = null;

    public AutoCompleteAdapter(final Context context, ITokenResource tokenResource) {
        super(context,-1);
        this.broToken = new BroToken(tokenResource);
    }

    public AutoCompleteAdapter(final Context context, int resource, ITokenResource tokenResource )  {
        super(context,resource);
        this.broToken = new BroToken(tokenResource);
    }

    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {

            @Override
            protected FilterResults performFiltering(final CharSequence constraint) {

                List<String> stringList = new ArrayList<String>();
                String word = (String)constraint;

                if (broToken.isToken(word)) {
                    try {
                        stringList = broToken.getSuggestions(word);
                    }
                    catch (Exception ex) { }
                }

                final FilterResults filterResults = new FilterResults();
                filterResults.values = stringList;
                filterResults.count = stringList.size();

                return filterResults;
            }

            @java.lang.SuppressWarnings("unchecked")
            @Override
            protected void publishResults(final CharSequence constraint, final FilterResults results) {
                clear();
                for (String tag : (List<String>) results.values) {
                    add(tag);
                }
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }

            @Override
            public CharSequence convertResultToString(final Object resultValue) {
                return (String)resultValue;
            }
        };
        return myFilter;
    }
}
