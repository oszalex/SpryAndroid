package com.getbro.bro.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by chris on 04/05/14.
 */

@DatabaseTable
public class User implements Serializable {

    @DatabaseField(generatedId=true)
    private int id;

    @SerializedName("id")
    @DatabaseField
    @Expose
    public long RemoteId;

    @SerializedName("sex")
    @DatabaseField
    @Expose
    public String Sex;

    @SerializedName("username")
    @DatabaseField
    @Expose
    public String UserName;

    @SerializedName("followed")
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    @Expose
    public long[] Followed; //ids of followed

    @SerializedName("follower")
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    @Expose
    public long[] Follower; //ids of follower


    public User(String Sex, String UserName, long followed[], long follower[]){
        this.Sex = Sex;
        this.UserName = UserName;
        this.Followed = followed;
        this.Follower = follower;
    }

    public int numOfFollowers(){
        return (Follower == null) ? 0 : Follower.length;
    }

    public int numOfFollowed(){
        return (Follower == null) ? 0 : Follower.length;
    }
}
