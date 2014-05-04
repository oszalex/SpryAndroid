package com.getbro.bro.Model;

import android.content.Context;

import com.getbro.bro.Json.User;
import com.orm.SugarRecord;

/**
 * Created by chris on 04/05/14.
 */
public class UserModel extends SugarRecord<UserModel> {
    public String Sex;
    public String UserName;
    public int followed[]; //ids of followed
    public int follower[]; //ids of follower

    public UserModel(Context ctx, String Sex, String UserName, int followed[], int follower[]){
        super(ctx);
        this.Sex = Sex;
        this.UserName = UserName;
        this.followed = followed;
        this.follower = follower;
    }
}
