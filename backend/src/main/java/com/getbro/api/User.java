package com.getbro.api;

import javax.xml.bind.annotation.*;
@XmlRootElement
public class User {
	public String name;
	public int age;
	public int userID;
	
	public static int ID;
	
	public User() {} 
   
	public User(String name, int age) {
		ID++;
		this.name = name;
		this.age = age;
		this.userID = ID;
	}
 }