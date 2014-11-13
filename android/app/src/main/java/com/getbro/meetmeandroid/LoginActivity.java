package com.getbro.meetmeandroid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.getbro.meetmeandroid.generate.LocalSession;
import com.getbro.meetmeandroid.generate.Settings;
import com.getbro.meetmeandroid.remote.state.AuthState;
import com.getbro.meetmeandroid.remote.state.RegisterState;
import com.getbro.meetmeandroid.remote.RemoteCallback;
import com.getbro.meetmeandroid.remote.RemoteResponse;
import com.getbro.meetmeandroid.remote.coord.SequenceState;


public class LoginActivity extends Activity {

    private AutoCompleteTextView mEmailView;
    private EditText mPhoneField;
    private View mProgressView;
    private View mEmailLoginFormView;
    private View mSignOutButtons;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        mLoginFormView = findViewById(R.id.login_form);

        mPhoneField = (EditText) findViewById(R.id.password);
        mPhoneField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                String phoneNumber = getPhoneNumber();
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin(phoneNumber);
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = getPhoneNumber();
                attemptLogin(phoneNumber);
            }
        });
    }

    public String getPhoneNumber() {
        return "436643947462";
    }


    /**
     * Attempt to login using the phone number
     */
    public void attemptLogin(String phoneNumber) {

        MeetMeApp app = (MeetMeApp)getApplication();

        RegisterState register = new RegisterState(phoneNumber, app.getCtx());
        register.setCallback(registerCallback);
        app.getCtx().invoke(register);

        showProgress(true);
    }

    private RemoteCallback registerCallback = new RemoteCallback() {
        @Override
        public void onRequestOk(RemoteResponse response) {

            try { Thread.sleep(2000); } catch (InterruptedException e) {}

            MeetMeApp app = (MeetMeApp)getApplication();
            AuthState auth = new AuthState(getPhoneNumber(), "1234", app.getCtx());
            auth.setCallback(authCallback);
            app.getCtx().invoke(auth);
        }

        @Override
        public void onRequestFailed(RemoteResponse response) {
            showProgress(false);
            Toast.makeText(LoginActivity.this, "failed to register!", Toast.LENGTH_LONG).show();
        }
    };

    private RemoteCallback authCallback = new RemoteCallback() {
        @Override
        public void onRequestOk(RemoteResponse response) {
            MeetMeApp app = (MeetMeApp)getApplication();
            LocalSession session = app.getSession();
            Settings settings = new Settings();
            settings.setNumber("aaa");
            settings.setSecret("1234aaa");
            session.saveSettings(settings);

            showProgress(false);

            finish();
        }

        @Override
        public void onRequestFailed(RemoteResponse response) {
            showProgress(false);
            Toast.makeText(LoginActivity.this, "failed to auth!", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}



