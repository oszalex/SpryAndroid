package com.gospry.remote.serialize;

import com.google.gson.JsonObject;
import com.gospry.Invitation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rich on 13.11.14.
 */
public class InvitationSerializer extends BaseSerializer<Invitation> {

    @Override
    protected Map<String, String> getRemoteResolutionMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("invitationId", "minvitationId");
        map.put("invitedUser", "minvitedUser");
        map.put("happeningId", "mhappeningId");
        //map.put("location","mLocation");
        return map;
    }

    @Override
    protected Invitation newObject() {
        return new Invitation();
    }

    @Override
    public void onSerialize(Invitation target, JsonObject data) {

    }

    @Override
    public void onDeserialize(Invitation target, JsonObject data) {

    }
}
