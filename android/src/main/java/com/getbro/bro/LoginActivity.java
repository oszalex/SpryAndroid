package com.getbro.bro;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.getbro.bro.Webservice.AsyncLoginCheck;
import com.getbro.bro.Webservice.AsyncLoginResponse;
import com.getbro.bro.Webservice.HttpGetRequest;


public class LoginActivity extends Activity implements AsyncLoginResponse{
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final String LOGIN_PREFS = "LoginCredentials";
    SharedPreferences settings;
    private String username;
    private String password;
    public HttpGetRequest httpRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        httpRequest = (HttpGetRequest)getApplication();


        //TODO: check credentials if stored
        //TODO: getPreferences instead of SHaredPreferences for performance

        settings = getSharedPreferences(LOGIN_PREFS, 0);

        username = settings.getString("username", null);
        password = settings.getString("password", null);

        if(username != null && password != null){
            Log.d(TAG, "Settings found. will test it now");
            checkLogin();
        }else {
            Log.d(TAG, "no suitable credentials found");
        }

    }

    public void checkLogin(){
        //deactivate elements
        enableDisableViewGroup((ViewGroup)getWindow().getDecorView().getRootView(), false);

        new AsyncLoginCheck(this, username, password).execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);
            if (view instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) view, enabled);
            }
        }
    }



    public void login(View view) {
     HttpGetRequest httpRequest = (HttpGetRequest)getApplication();
     EditText mUsername = (EditText)findViewById(R.id.login_username);
     EditText mPassword = (EditText)findViewById(R.id.login_password);

     username = mUsername.getText().toString();
     password = mPassword.getText().toString();

     checkLogin();
    }



    @Override
    public HttpGetRequest getHTTPRequest() {
        return httpRequest;
    }

    @Override
    public void onLoginCheckFinish(Boolean output) {
        if(output){
            Log.d(TAG, "store credentials");
            SharedPreferences.Editor editor = settings.edit();

            editor.putString("username", username);
            editor.putString("password", password);

            editor.commit();

            this.finish();
        }else {
            enableDisableViewGroup((ViewGroup)getWindow().getDecorView().getRootView(), true);
        }


        //TODO: else print error.
    }
}
