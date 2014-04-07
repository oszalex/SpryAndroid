package com.getbro.bro;

import java.util.ArrayList;
import java.util.List;

public class Event {

	public String string;
	
	//Details zu den Events
	//TODO umbenennen
	public final List<String> children = new ArrayList<String>();

	public Event(String string) {
		this.string = string;
	}
}