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

    public static boolean activateUser(Activity act,int code)
    {
        try{
            JSONObject x = new JSONObject();
            x.put("code", code);
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
