package com.getbro.bro;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.getbro.bro.Auth.AuthManager;
import com.getbro.bro.Auth.UserAccount;
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

    public Boolean checkLogin(UserAccount credentials){
        // deactivate elements
        enableDisableViewGroup((ViewGroup)getWindow().getDecorView().getRootView(), false);

        // check credentials
        try {
            return new AsyncTask<UserAccount,Void, Boolean>() {

                private boolean checkCredentials(UserAccount uc){
                    Log.d("Login", "Check credentials");

                    HttpGetRequest hr = HttpGetRequest.getHttpGetRequest();
                    hr.configureClient(uc.username, uc.password);

                    return hr.checkLogin();
                }

                @Override
                protected Boolean doInBackground(UserAccount... params) {
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

        UserAccount uc = new UserAccount(username, password);

        if(checkLogin(uc)){
            Log.d(TAG, "user logged in. close loginform");
            AuthManager.setAccout(uc);

            this.finish();

        }else {
            Log.d(TAG, "wrong credentials");

            enableDisableViewGroup((ViewGroup)getWindow().getDecorView().getRootView(), true);
        }


    }


}
