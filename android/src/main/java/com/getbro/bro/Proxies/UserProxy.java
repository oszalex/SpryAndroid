package com.getbro.bro.Proxies;

import com.getbro.bro.Json.User;
import com.getbro.bro.Model.UserModel;
import com.getbro.bro.Webservice.AsyncLoginResponse;
import com.getbro.bro.Webservice.HttpGetRequest;

/**
 * Created by chris on 04/05/14.
 */
public class UserProxy {

    public static UserModel getUser(AsyncLoginResponse ac, long id){

        UserModel u = UserModel.findById(UserModel.class, id);

        //try to find it on the internet
        if(u == null){
            //TODO: exceptionhandling no inet connection

            HttpGetRequest server = ac.getHTTPRequest();
            User user = server.getUser(id);
            u = new UserModel(
                    ac.getApplicationContext(),
                    user.Sex,
                    user.UserName,
                    user.Followed,
                    user.Follower
            );

            u.save();

        }

        return u;
    }
}
