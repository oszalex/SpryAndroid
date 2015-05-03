package com.gospry;

/**
 * Created by lexy on 03.05.15.
 */
public enum InvitationStatus {
    INVITED("invited"), NOT_INVITED("not_invited"), ATTENDING("attending"), MAYBE("maybe"), NOT_ATTENDING("not_attending");
    private final String value;

    private InvitationStatus(String value) {
        this.value = value;
    }
}
