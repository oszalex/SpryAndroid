package com.getbro.bro.Data;

import com.getbro.bro.Webservice.AsyncLoginResponse;
import com.getbro.bro.Webservice.HttpGetRequest;

/**
 * Created by chris on 5/7/14.
 */

public class EventProxy {

    public static Event getUser(AsyncLoginResponse ac, long id){

        Event e = Event.findById(Event.class, id);

        //try to find it on the internet
        if(e == null){
            //TODO: exceptionhandling no inet connection

            HttpGetRequest server = ac.getHTTPRequest();
            e = server.getEvent(id);

            e.save();

        }

        return e;
    }
}
