package com.getbro.bro.Data;

import com.getbro.bro.Data.User;
import com.getbro.bro.Webservice.AsyncLoginResponse;
import com.getbro.bro.Webservice.HttpGetRequest;

/**
 * Created by chris on 04/05/14.
 */
public class UserProxy {

    public static User getUser(AsyncLoginResponse ac, long id){

        User u = User.findById(User.class, id);

        //try to find it on the internet
        if(u == null){
            //TODO: exceptionhandling no inet connection

            HttpGetRequest server = ac.getHTTPRequest();
            u = server.getUser(id);

            u.save();

        }

        return u;
    }
}
