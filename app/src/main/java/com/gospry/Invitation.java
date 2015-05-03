package com.gospry;

import android.content.Context;

/**
 * Created by lexy on 03.05.15.
 */
public class Invitation {
    String minvitedUser;
    String musername = "";
    Long minvitationId;
    InvitationStatus mstatus;
    Long mhappeningId;
    boolean isModerator;

    public Invitation() {

    }

    public boolean isModerator() {
        return isModerator;
    }

    public String getUsername(Context context) {
        if (musername == "") this.musername = MeetMeApp.getContactName(minvitedUser, context);
        return musername;
    }

    public String getNumber() {
        return minvitedUser;
    }

    public InvitationStatus getStatus() {
        return mstatus;
    }

    public void setStatus(String status) {
        status = status.toUpperCase();
        status = status.replace("\"", "");
        if (status.equals("INVITED"))
            this.mstatus = InvitationStatus.INVITED;
        else if (status.equals("NOT_ATTENDING"))
            this.mstatus = InvitationStatus.NOT_ATTENDING;
        else if (status.equals("ATTENDING"))
            this.mstatus = InvitationStatus.ATTENDING;
        else if (status.equals("MAYBE"))
            this.mstatus = InvitationStatus.MAYBE;
        else this.mstatus = InvitationStatus.NOT_INVITED;
    }
}
