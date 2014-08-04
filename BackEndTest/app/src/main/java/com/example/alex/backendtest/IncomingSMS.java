package com.example.alex.backendtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class IncomingSMS extends BroadcastReceiver {

    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
                    int index = message.indexOf("Meetme:");
                    Log.i("Binda", "Index: " + index);
                   /* if( index > -1 ) {
                        Intent intentx = new Intent(context, FirstRun.class);
                        intentx.putExtra("msgContent", message.substring(index+7));
                        intentx.putExtra("sender",senderNum);
                        intentx.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     //   context.startActivity(intentx);
                    }
*/
                    // Show Alert
                    //int duration = Toast.LENGTH_LONG;
                  //  Toast toast = Toast.makeText(context,
                  //          "senderNum: "+ senderNum + ", message: " + message, duration);
                  //  toast.show();

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }
}
