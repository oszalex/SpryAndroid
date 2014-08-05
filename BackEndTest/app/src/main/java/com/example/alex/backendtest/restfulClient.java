package com.example.alex.backendtest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * Created by Alex on 05.08.2014.
 */
public class restfulClient {

    public static HttpResponse createUser(Activity act,long phonenumber)
    {

        try {
            //Create Keys
            KeyPairGenerator keygenerator = null;
            keygenerator = KeyPairGenerator.getInstance("RSA");
            final KeyPair myKeyPair = keygenerator.genKeyPair();
            //Store Keys
            String privatekey = Keys.savePrivateKey(myKeyPair.getPrivate());
            String publickey = Keys.savePublicKey(myKeyPair.getPublic());
            SharedPreferences sharedPref = act.getSharedPreferences("KeyPair",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            Log.i("Privatekey", privatekey);
            Log.i("Publickey", publickey);
            editor.putString("privateKey", privatekey);
            editor.putString("publicKey", privatekey);
            editor.putString("userID", Long.toString(phonenumber));
            editor.commit();

            //Create JSON
            JSONObject x = new JSONObject();
            x.put("key", publickey);
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

    public static HttpResponse activateUser(int code)
    {
        //Send Code
        //Check Code
        return null;
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
