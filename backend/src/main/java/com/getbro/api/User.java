package com.getbro.api;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;

@XmlRootElement
public class User {
	private String name;
	private int age;
	private BigInteger userID;
	
	public static int ID;
	
	public User() {} 
   
	public User(String name, int age) {
		ID++;
		this.name = name;
		this.age = age;
		this.userID = BigInteger.valueOf(ID);
	}
	public User(User user) {
		ID++;
		this.name = user.name;
		this.age = 20;
		this.userID = BigInteger.valueOf(ID);
	}

	@XmlElement(name="name")
	public String getUsername(){
		return name;
	}
	public void setUsername(String name){	
		this.name=name;
	}

	@XmlElement(name="id")
	public BigInteger getId(){
		return userID;
	}
	public void setId(int ID){
		this.userID=BigInteger.valueOf(this.ID);
	}
 }