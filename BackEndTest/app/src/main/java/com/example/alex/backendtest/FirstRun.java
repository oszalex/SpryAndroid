package com.example.alex.backendtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.backendtest.R;

import org.apache.http.HttpResponse;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class FirstRun extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);
        EditText et = (EditText) findViewById(R.id.phonenumber);
        TelephonyManager tMgr = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        et.setText(mPhoneNumber);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String code = extras.getString("msgContent");
            Log.i("Code Activation", "code");
            String messageSender = extras.getString("sender");
            EditText et2 = (EditText) findViewById(R.id.code);
            et.setText(code);
            Intent returnIntent = new Intent();
            restfulClient.activateUser(this,Integer.parseInt( code));
            /*if(activate(code,this)) {
                Toast.makeText(this, "Wrong activation Code", Toast.LENGTH_SHORT).show();
            }
            else{
                setResult(RESULT_OK, returnIntent);
                finish();
            }*/
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.first_run, menu);
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

    private static final String MY_PREFERENCES = "my_preferences";

    public static boolean isFirst(Context context){
        final SharedPreferences reader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        final boolean first = reader.getBoolean("is_first", false);
      /*  if(!first){
            final SharedPreferences.Editor editor = reader.edit();
            editor.putBoolean("is_first", first);
            editor.commit();
        }
        */
        return first;
    }
    public static boolean setFirst(Context context){
        final SharedPreferences reader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        final boolean first = reader.getBoolean("is_first", false);
        if(!first){
            final SharedPreferences.Editor editor = reader.edit();
            editor.putBoolean("is_first", first);
            editor.commit();
        }
        return first;
    }
    public int activationcode;
    public void register(View v)
    {   //JSONObject x = comm.JSONcreator(this, new String[] {"phonenumber"});
      //  comm.sendJason(Main.URI + "/users", x);
      //  new HttpPostx().execute(Main.URI + "/users",x.toString());
        int resId = this.getResources().getIdentifier("phonenumber", "id", this.getPackageName());
        EditText x = (EditText) this.findViewById(resId);
        restfulClient.createUser(this,Long.parseLong(x.getText().toString().replace("+","")));
        Log.i("Registering user", x.getText().toString());
        Toast.makeText(this, "USer created", Toast.LENGTH_SHORT).show();
    }

    public void check(View v)
    {
        EditText et = (EditText) findViewById(R.id.phonenumber );
        String number = et.getText().toString().replace("+","");
        int resId = this.getResources().getIdentifier("code", "id", this.getPackageName());
        EditText x = (EditText) this.findViewById(resId);
        restfulClient.activateUser(this,Integer.parseInt( x.getText().toString()));
    }

}
