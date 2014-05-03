package com.getbro.bro;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.getbro.bro.Json.User;
import com.getbro.bro.Webservice.HttpGetRequest;

import java.util.ArrayList;


public class LoginActivity extends Activity {
    private HttpGetRequest httpRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        httpRequest = (HttpGetRequest)getApplication();
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



    public void login(View view) {
     HttpGetRequest httpRequest = (HttpGetRequest)getApplication();
     EditText mUsername = (EditText)findViewById(R.id.login_username);
     EditText mPassword = (EditText)findViewById(R.id.login_password);

     String username = mUsername.getText().toString();
     String password = mPassword.getText().toString();

     new LoginCheck(this, username, password).execute();

    }



    private class LoginCheck extends AsyncTask<Void,Void, Boolean> {
        private String username;
        private String password;
        Activity activity;

        LoginCheck(Activity activity, String username, String password) {

            Log.d("Login", "new LoginCheck");

            this.activity = activity;
            this.username = username;
            this.password = password;
        }

        private boolean checkCredentials(){
            httpRequest.configureClient(getResources().getString(R.string.webService), username, password);

            Log.d("Login", "Check credentials");

            return httpRequest.checkLogin();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Log.d("Login", "some background work");
            return checkCredentials();
        }

        protected void onPostExecute(Boolean result) {
            Log.d("Login", "Resultat ist da!");
            if(result)
                activity.finish();

        }
    }

}
