package com.gospry;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.gospry.remote.RemoteCallback;
import com.gospry.remote.RemoteResponse;
import com.gospry.remote.state.PostAuthState;
import com.gospry.remote.state.PostRegisterState;
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
import java.util.Map;


public class LoginActivity extends Activity {

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private EditText phoneText;
    private View loginFormView;
    private Spinner countrySpinner;
    private TextView countryCodeLabel;
    private Map<String, String> iso2Prefix = new HashMap<String, String>();
    private ArrayList<String> countryList;
    private ArrayList<String> countryListAlpha2;
    private String prefix;
    private String phoneNumber;
    private ProgressDialog progressDialog;
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
    private RemoteCallback registerCallback = new RemoteCallback() {
        @Override
        public void onRequestOk(RemoteResponse response) {

            String token = "1234"; // get this from the contacts
            // remove the following
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }


            MeetMeApp app = (MeetMeApp) getApplication();
            PostAuthState auth = new PostAuthState(getPhoneNumber(), token, app.getCtx());
            auth.setCallback(authCallback);
            app.getCtx().invoke(auth);
        }

        @Override
        public void onRequestFailed(RemoteResponse response) {
            showProgress(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        //TODO: Register with google
        Intent it = new Intent(this, DemoActivity.class);
        startActivity(it);
        loginFormView = findViewById(R.id.login_form);

        phoneText = (EditText) findViewById(R.id.phoneNumber);
        phoneText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    phoneNumber = phoneText.getText().toString();
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        if (BuildConfig.DEBUG) {
           // phoneText.setText("664" + Integer.toString(1000 + new Random().nextInt(1000)));
            phoneText.setText("6802118976");
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
        //TODO: get country code from sim
        selectCountry(CountryCode.AT.getAlpha2(), true);

        if (checkPlayServices()) {
            //TODO:
            // If this check succeeds, proceed with normal processing.
            // Otherwise, prompt user to get valid Play Services APK.
        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("ABC", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
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
        return (prefix.replaceAll("\\+", "") + phoneNumber).trim();
    }

    @Override
    public void onBackPressed() {
    }

    /**
     * Attempt to login using the phone number
     */
    public void attemptLogin() {

        MeetMeApp app = (MeetMeApp) getApplication();
        SharedPreferences prefs = getSharedPreferences(DemoActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
        Log.i("Property Reg ID during register", prefs.getString(DemoActivity.PROPERTY_REG_ID, ""));
        PostRegisterState register = new PostRegisterState(getPhoneNumber(), app.getCtx(), prefs.getString(DemoActivity.PROPERTY_REG_ID, ""));
        register.setCallback(registerCallback);
        app.getCtx().invoke(register);
        showProgress(true);
    }

    public void showProgress(final boolean show) {
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
}



