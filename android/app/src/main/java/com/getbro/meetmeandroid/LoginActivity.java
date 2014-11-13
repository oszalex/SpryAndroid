package com.getbro.meetmeandroid;

import android.annotation.TargetApi;

import android.app.Activity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.getbro.meetmeandroid.remote.state.AuthState;
import com.getbro.meetmeandroid.remote.state.RegisterState;
import com.getbro.meetmeandroid.remote.RemoteCallback;
import com.getbro.meetmeandroid.remote.RemoteResponse;
import com.neovisionaries.i18n.CountryCode;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;


public class LoginActivity extends Activity {

    private EditText phoneText;
    private View loginFormView;
    private Spinner countrySpinner;
    private TextView countryCodeLabel;

    private Map<String,String> iso2Prefix = new HashMap<String, String>();
    private ArrayList<String> countryList;
    private ArrayList<String> countryListAlpha2;

    private String prefix;
    private String phoneNumber;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        loginFormView = findViewById(R.id.login_form);

        phoneText = (EditText) findViewById(R.id.phoneNumber);
        phoneText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                String phoneNumber = getPhoneNumber();
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    phoneNumber = phoneText.getText().toString();
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        if (BuildConfig.DEBUG) {
            phoneText.setText("664" + Integer.toString(1000 + new Random().nextInt(1000)));
        }

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber = phoneText.getText().toString();
                attemptLogin();
            }
        });

        countrySpinner = (Spinner) findViewById(R.id.country_spinner);
        countryCodeLabel = (TextView) findViewById(R.id.country_phone_prefix);

        preloadIso2Prefix();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapter);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String code = countryListAlpha2.get(position);
                selectCountry(code, false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectCountry(CountryCode.getByLocale(Locale.getDefault()).getAlpha2(), true);
    }

    private void selectCountry(String code, boolean setSpinner) {
        if (setSpinner) {
            for (int i = 0; i < countryListAlpha2.size(); i++) {
                if (code.equals(countryListAlpha2.get(i))) {
                    countrySpinner.setSelection(i);
                    break;
                }
            }
        }
        countryCodeLabel.setText(iso2Prefix.get(code));
        prefix = iso2Prefix.get(code);
    }

    private void preloadIso2Prefix() {
        countryList = new ArrayList<String>();
        countryListAlpha2 = new ArrayList<String>();
        try {
            InputStream input = getAssets().open("countrylist.csv");
            CSVParser parser = new CSVParser(new InputStreamReader(input), CSVFormat.DEFAULT);

            Iterator<CSVRecord> recordIterator = parser.iterator();
            recordIterator.next();
            while (recordIterator.hasNext()) {
                CSVRecord record = recordIterator.next();
                String code = record.get(10);
                String phonePrefix = record.get(9);
                CountryCode cc = CountryCode.getByCode(code);
                if (cc != null) {
                    iso2Prefix.put(code, phonePrefix);
                    countryList.add(cc.getName());
                    countryListAlpha2.add(cc.getAlpha2());
                }
            }
        } catch (IOException e) {
        }
    }

    public String getPhoneNumber() {
        return prefix.replaceAll("\\+","") + phoneNumber;
    }

    @Override
    public void onBackPressed() {
    }

    /**
     * Attempt to login using the phone number
     */
    public void attemptLogin() {

        MeetMeApp app = (MeetMeApp)getApplication();

        RegisterState register = new RegisterState(getPhoneNumber(), app.getCtx());
        register.setCallback(registerCallback);
        app.getCtx().invoke(register);

        showProgress(true);
    }

    private RemoteCallback registerCallback = new RemoteCallback() {
        @Override
        public void onRequestOk(RemoteResponse response) {

            String token = "1234"; // get this from the contacts
            // remove the following
            try { Thread.sleep(2000); } catch (InterruptedException e) {}


            MeetMeApp app = (MeetMeApp)getApplication();
            AuthState auth = new AuthState(getPhoneNumber(), token, app.getCtx());
            auth.setCallback(authCallback);
            app.getCtx().invoke(auth);
        }

        @Override
        public void onRequestFailed(RemoteResponse response) {
            showProgress(false);
        }
    };

    private RemoteCallback authCallback = new RemoteCallback() {
        @Override
        public void onRequestOk(RemoteResponse response) {
            showProgress(false);
            finish();
        }

        @Override
        public void onRequestFailed(RemoteResponse response) {
            showProgress(false);
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
        loginFormView.post(new Runnable() {
            @Override
            public void run() {
                if (show) {
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage(getString(R.string.signin_please_wait));
                    progressDialog.show();
                } else {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                }
            }
        });
    }
}



