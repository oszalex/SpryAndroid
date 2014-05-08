package com.getbro.bro;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.getbro.bro.Auth.AuthManager;
import com.getbro.bro.Auth.UserCredentials;
import com.getbro.bro.Webservice.HttpGetRequest;

import java.util.concurrent.ExecutionException;


public class LoginActivity extends Activity{
    private static final String TAG = LoginActivity.class.getSimpleName();
    private String username;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public Boolean checkLogin(UserCredentials credentials){
        // deactivate elements
        enableDisableViewGroup((ViewGroup)getWindow().getDecorView().getRootView(), false);

        // check credentials
        try {
            return new AsyncTask<UserCredentials,Void, Boolean>() {

                private boolean checkCredentials(UserCredentials uc){
                    Log.d("Login", "Check credentials");

                    HttpGetRequest hr = HttpGetRequest.getHttpGetRequest();
                    hr.configureClient(uc.username, uc.password);

                    return hr.checkLogin();
                }

                @Override
                protected Boolean doInBackground(UserCredentials... params) {
                    Log.d("Login", "some background work");
                    return checkCredentials(params[0]);
                }

            }.execute(credentials).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }


    private static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
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

        EditText mUsername = (EditText)findViewById(R.id.login_username);
        EditText mPassword = (EditText)findViewById(R.id.login_password);

        username = mUsername.getText().toString();
        password = mPassword.getText().toString();

        UserCredentials uc = new UserCredentials(username, password);

        if(checkLogin(uc)){
            //right credentials
            AuthManager.setAccout(uc);

            this.finish();

        }else {
            //wrong credentials

            enableDisableViewGroup((ViewGroup)getWindow().getDecorView().getRootView(), true);
        }


    }


}
