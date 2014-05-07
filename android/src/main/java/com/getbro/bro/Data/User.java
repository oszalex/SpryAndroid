package com.getbro.bro.Data;

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
    public long RemoteId;

    @SerializedName("sex")
    @DatabaseField
    public String Sex;

    @SerializedName("username")
    @DatabaseField
    public String UserName;

    @SerializedName("followed")
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    public long[] Followed; //ids of followed

    @SerializedName("follower")
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    public long[] Follower; //ids of follower

    public User(){}

    public User(String Sex, String UserName, long followed[], long follower[]){
        this.Sex = Sex;
        this.UserName = UserName;
        this.Followed = followed;
        this.Follower = follower;
    }
}
