package com.getbro.bro.Auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


public class AuthManager {
    private static final String TAG = AuthManager.class.getSimpleName();
    private static final String LOGIN_PREFS = "LoginCredentials";
    private static AuthManager am;
    private static SharedPreferences settings;
    private static UserAccount creds = null;
    private static boolean init = false;

    private AuthManager(Context ctx){
        settings = ctx.getSharedPreferences(LOGIN_PREFS, 0);

        //try to find values

        String username = settings.getString("username", null);
        String password = settings.getString("password", null);
        Long id = settings.getLong("id", 0);

        if(username != null && password != null){
            creds = new UserAccount(username, password);
            Log.d(TAG, "existing account found: " + creds);
        }
    }

    public static AuthManager init(Context ctx){
        if(am == null) am = new AuthManager(ctx);

        return am;
    }

    public static UserAccount getAccount(){
        Log.d(TAG, "fetch UserAccount");
        return creds;
    }

    public static void setAccout(UserAccount uc){
        SharedPreferences.Editor editor = settings.edit();

        editor.putString("username", uc.getUsername());
        editor.putString("password", uc.getPassword());

        if(uc.getId() != null)
            editor.putLong("id", uc.getId());

        editor.commit();

        creds = uc;
    }

    public static void setID(long id){
        SharedPreferences.Editor editor = settings.edit();

        editor.putLong("id", id);

        editor.commit();
    }


    static class AuthManagerNotInitalizedException extends RuntimeException{
    }

}


