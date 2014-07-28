package com.getbro.api;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;
import java.util.Random;

@XmlRootElement
public class User {
	private String name;
	private String phonenumber;
	private int age;
	private BigInteger userID;
	
	private boolean activated = false;
	private int activationcode;
	private BigInteger telnr;
	public static int ID;
	
	public User() {} 
   
	public User(String name, int age) {
		ID++;
		this.name = name;
		this.age = age;
		this.userID = BigInteger.valueOf(ID);
		this.phonenumber="123";
		this.activationcode = 2345;
		this.activated = true;
		this.telnr = BigInteger.valueOf(456);
	}
	public User(User user) {
		ID++;
		this.name = user.name;
		this.age = 20;
		this.userID = BigInteger.valueOf(ID);
		this.phonenumber="234";
		this.activated = false;
		this.activationcode = 1234;
		this.telnr = BigInteger.valueOf(456);
	}
	
	public int sendConfirmation()
	{	
		Random randomGenerator = new Random();
		activationcode = randomGenerator.nextInt(99999);
		//Sende SMS an Handynummer mit genereiertem Code
		System.out.println("Sending Activation Code " + activationcode);
		return 1;
	}
	public boolean checkActivation(int code)
	{	
		if(code == activationcode){
			System.out.println("Code OK " +activationcode);
			return true;
		}
		else{
			System.out.println("Wrong Code " +code);
			return false;
		}
	}
	
	
	@XmlElement(name="name")
	public String getUsername(){
		return name;
	}
	public void setUsername(String name){	
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
		//return new String(telnr.toByteArray());
		//System.out.println("Number: " + telnr);
		//return "blabla";
	}
	public void setId(String phonenumber){
		
		this.telnr = new BigInteger(phonenumber);
		System.out.println("Number: " +phonenumber+"  " + telnr);
		//this.phonenumber=phonenumber;
	}
	@XmlElement(name="code")
	public String getCode(){
		//return telnr.toString();
		return Integer.toString(activationcode);
		//return activationcode;
	}
	public void setCode(String code){
		this.activationcode = new Integer(code);
		System.out.println("Code: " +code);
		//this.phonenumber=phonenumber;
	}
 }