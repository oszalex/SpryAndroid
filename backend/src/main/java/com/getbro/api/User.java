package com.getbro.api;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;

@XmlRootElement
public class User {
	private String name;
	private String phonenumber;
	private int age;
	private BigInteger userID;
	
	private boolean activated = false;
	public int activationcode;
	private BigInteger telnr;
	public static int ID;
	
	public User() {} 
   
	public User(String name, int age) {
		ID++;
		this.name = name;
		this.age = age;
		this.userID = BigInteger.valueOf(ID);
		this.phonenumber="123";
	}
	public User(User user) {
		ID++;
		this.name = user.name;
		this.age = 20;
		this.userID = BigInteger.valueOf(ID);
		this.phonenumber="234";
	}
	
	public int sendConfirmation()
	{
		//Sende SMS an Handynummer mit genereiertem Code
		System.out.println("Sending Textmessage");
		activationcode = 1234;
		return 1;
	}
	public int checkActivation(int code)
	{	
		System.out.println("User activated");
		//if(code==1234) return 1;
		//else return 0;
		return 1;
	}
	@XmlElement(name="name")
	public String getUsername(){
		return name;
	}
	public void setUsername(String name){	
	//	System.out.println("Name: " +name);
		this.name=name;
	}
/*	@XmlElement(name="phonenumber")
	public BigInteger phonenumber(){
		return userID;
	}
	public void phonenumber(String phonenumber){
		System.out.println("Test 2" +phonenumber);
		this.userID= BigInteger.valueOf(200);
	}*/
	/*@XmlElement(name="code")
	public String getCode(){
		return activationcode;
	}
	public void setCode(String code){
		
		this.activationcode = Integer.parseInt(code);
		System.out.println("Hier: code " + code);
		//this.phonenumber=phonenumber;
	}*/
	@XmlElement(name="phonenumber")
	public String getId(){
		return telnr.toString();
	}
	public void setId(String phonenumber){
		
		this.telnr = new BigInteger(phonenumber);
		System.out.println("Number: " +phonenumber+"  " + telnr);
		//this.phonenumber=phonenumber;
	}
 }