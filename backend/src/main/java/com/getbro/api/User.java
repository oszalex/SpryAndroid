package com.getbro.api;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;
import java.util.Random;
import java.security.*;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.*;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;
@XmlRootElement
public class User {
	
	//public static long ID;
	//private int birthdate;
	//private long userID;
	
	private String name;
	private long phonenumber;
	private boolean activated = false;
	private PublicKey publicKey;
	public User() {} 

	public User(String name, long phonenumber) {
		//ID++;
		//this.userID = ID;
		this.name = name;
		this.phonenumber = phonenumber;
		this.activated = true;
	}
	public User(User user) {
		//ID++;
		this.name = user.getUsername();
		this.phonenumber=Long.parseLong(user.getId());
		this.activated = false;
	}
	
	public int sendConfirmation(){	
	//	Random randomGenerator = new Random();
	//	activationcode = randomGenerator.nextInt(99999);
		//Sende SMS an Handynummer mit genereiertem Code
		System.out.println("Sending Activation Code " + activationcode());
		return 1;
	}
	public boolean checkActivation(int code){	
		if(code == activationcode()){
			System.out.println("Code OK " +activationcode());
			return true;
		}
		else{
			System.out.println("Wrong Code " +code);
			return false;
		}
	}
	
	private int activationcode()
	{
		String digits = Long.toString(phonenumber);
		int x = Integer.parseInt(digits.substring(digits.length()-4,digits.length()));
		x += Integer.parseInt(Long.toString(phonenumber).substring(0,4));
		return x % 10000;
	}
	@XmlElement(name="name")
	public String getUsername(){
		return name;
	}
	public void setUsername(String name){	
		this.name=name;
	}

	@XmlElement(name="userId")
	public String getId(){
		return Long.toString(phonenumber);
		//return new String(telnr.toByteArray());
		//System.out.println("Number: " + telnr);
		//return "blabla";
	}
	public void setId(String phonenumber){
		this.phonenumber = Long.parseLong(phonenumber);
		System.out.println("Number: " +phonenumber);
		//this.phonenumber=phonenumber;
	}
	/*@XmlElement(name="publicKey")
	public String publicKey(){
		try{
		return savePublicKey(this.publicKey);
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		//return new String(telnr.toByteArray());
		//System.out.println("Number: " + telnr);
		//return "blabla";
	}
	public void publicKey(String publicKey){
		try{
		this.publicKey = loadPublicKey(publicKey);
		System.out.println("SetKey: " + publicKey);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		//this.phonenumber=phonenumber;
	}*/
	public String decode(String signature)
	{
		return signature;	
	}
/*	@XmlElement(name="code")
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
	public static PublicKey loadPublicKey(String stored) throws GeneralSecurityException {
        byte[] data = DatatypeConverter.parseBase64Binary(stored);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        return fact.generatePublic(spec);
    }


    public static String savePublicKey(PublicKey publ) throws GeneralSecurityException {
        KeyFactory fact = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec spec = fact.getKeySpec(publ,
                X509EncodedKeySpec.class);
        return DatatypeConverter.printBase64Binary(spec.getEncoded());
    }
 }