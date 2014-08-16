package com.getbro.api;

import com.getbro.api.items.Event;
import com.getbro.api.items.EventInvitation;
import com.getbro.api.items.InvitationStatus;
import com.getbro.api.items.User;

import java.util.*;

public class ApiStorageWrapper {
    public static HashMap<Long, User> users = new HashMap<Long, User>();
    public static HashMap<Long, Event> events = new HashMap<Long, Event>();

    public ApiStorageWrapper() {
        if (users.size() == 0) {

            users.put(4369911602033L, new User("Chris", 4369911602033L));
            users.put(436802118976L, new User("Alex", 436802118976L));

            Event x = new Event("me #hunger #essen @vapiano now!");
            events.put(x.getId(), x);
            x = new Event("kino heute @apollo 18:00 +chris +diana");
            events.put(x.getId(), x);

            x = new Event("rammelrudel morgen @alexgarten #public");


            x.invite(users.get(436802118976L),users.get(4369911602033L));
            events.put(x.getId(), x);
        }
    }
}