package com.getbro.bro.Data;

import android.content.Context;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Created by chris on 04/05/14.
 */
public class User extends SugarRecord<User> {

    @SerializedName("Id")
    public Long Id;

    @SerializedName("sex")
    public String Sex;

    @SerializedName("username")
    public String UserName;

    @SerializedName("followed")
    public Long[] Followed; //ids of followed

    @SerializedName("follower")
    public Long[] Follower; //ids of follower

    public User(Context ctx){
        super(ctx);
    }

    public User(Context ctx, String Sex, String UserName, Long followed[], Long follower[]){
        super(ctx);
        this.Sex = Sex;
        this.UserName = UserName;
        this.Followed = followed;
        this.Follower = follower;
    }

    public static User updateOrInsert (Context ctx, User user){
        if (user == null) return null;

        User u = User.findById(User.class, user.Id);

        if(null == u)
            u = user;

        u.save();

        return u;
    }
}
