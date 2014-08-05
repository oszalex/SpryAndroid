package com.example.alex.backendtest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * Created by Alex on 05.08.2014.
 */
public class restfulClient {
    static long  phonenumber;

    public static HttpResponse createUser(Activity act,long phonenumberx)
    {
        phonenumber=phonenumberx;
        try {
            //Create Keys
            KeyPairGenerator keygenerator = null;
            keygenerator = KeyPairGenerator.getInstance("RSA");
            final KeyPair myKeyPair = keygenerator.genKeyPair();
            //Store Keys
            String privatekey = Keys.savePrivateKey(myKeyPair.getPrivate());
            String publickey = Keys.savePublicKey(myKeyPair.getPublic());
            SharedPreferences sharedPref = act.getSharedPreferences("com.example.alex.backendtest",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            Log.i("Privatekey", privatekey);
            Log.i("Privatekey22", myKeyPair.getPrivate().toString());
            Log.i("Publickey", publickey);

            Log.i("Publickey22", myKeyPair.getPublic().toString());
            BigInteger bi = new BigInteger(
                    myKeyPair.getPublic().toString().substring(myKeyPair.getPublic().toString().indexOf("=")+1,myKeyPair.getPublic().toString().indexOf(",")),
                    16);
            Log.i("Base 10 Publickey", bi.toString(10));
            editor.putString("privateKey", privatekey);
            editor.putString("publicKey", publickey);
            editor.putString("userId", Long.toString(phonenumber));
            editor.commit();

            //Create JSON
            JSONObject x = new JSONObject();
            x.put("publicKey", publickey);
            //Post JSON
            new HttpPostx(act).execute(Main.URI + "/users",x.toString());
        }
        catch(Exception e)
        {
            Log.e("Error", e.toString());
            e.printStackTrace();
        }
        return null;
    }

    public static boolean activateUser(Activity act,int code)
    {
        try{
            JSONObject x = new JSONObject();
            x.put("code", Integer.toString(code));
            //Post JSON
            new HttpPostx(act).execute(Main.URI + "/users/" +phonenumber,x.toString());
            Log.i("Activating user", x.toString());
           /* if (y.getStatusLine().getStatusCode() != 200){
                Log.i("Activation Code wrong", "False ");
                return null;
            }
            else {
                Log.i("Activation Code accepted", "OK");
                return y;
                //  setResult(RESULT_CANCELED, returnIntent);
            }*/
        }catch(Exception e)
        {
            Log.e("Error", e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static HttpResponse createEvent(String raw)
    {
        //Auseinanderdividieren
        //Senden
        return null;
    }

    public static HttpResponse getEvents(String raw)
    {
        //Auseinanderdividieren
        //Senden
        return null;
    }
    public static HttpResponse getEvent(long eventID)
    {
        //Auseinanderdividieren
        //Senden
        return null;
    }
}
