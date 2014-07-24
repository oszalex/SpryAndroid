package com.getbro.api;

import java.util.*;
import java.math.BigInteger;
class ApiStorageWrapper{
	public static List<User> users = new LinkedList<User>();
	public static List<Event> events = new LinkedList<Event>();
	
	public ApiStorageWrapper()
	{
		if(users.size() == 0){

            users.add(new User("chris", 22));
            users.add(new User("alex", 433));

            events.add(new Event("me #hunger #essen @vapiano now!"));
            events.add(new Event("kino heute @apollo 18:00 +chris +diana"));


            Event e1 = new Event("rammelrudel morgen @alexgarten #public");
            e1.invite(new EventInvitation(0, BigInteger.valueOf(0), BigInteger.valueOf(0), InvitationStatus.INVITED));
            
            events.add(e1);

        }
	}
}