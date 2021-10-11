package gaaynako.transpay.crudandroidretrofit.config;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MySmsReceiver extends BroadcastReceiver {

    private static final String TAG =
            MySmsReceiver.class.getSimpleName();
    public static final String pdu_type = "pdus";
    public static  SharedPreferences sp;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the SMS message.
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        String strMessage = "";
        String format = bundle.getString("format");
        // Retrieve the SMS message received.
        Object[] pdus = (Object[]) bundle.get(pdu_type);
        if (pdus != null) {
            // Check the Android version.
            boolean isVersionM =
                    (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
            // Fill the msgs array.
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                // Check Android version and use appropriate createFromPdu.
                if (isVersionM) {
                    // If Android version M or newer:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    // If Android version L or older:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                // Build the message to show.
                strMessage += "SMS from " + msgs[i].getOriginatingAddress();
                strMessage += " :" + msgs[i].getMessageBody() + "\n";
                // Log and display the SMS message.
                Log.d(TAG, "onReceive: " + strMessage);
               /* SharedPreferences sh = context.getSharedPreferences("myshared",context.MODE_PRIVATE);
                Log.d(TAG, "sh: " + sh);*/
                sp = context.getSharedPreferences("myshared",context.MODE_PRIVATE);
                if (strMessage.contains("Test")){
                    Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("contentSms",""+ strMessage);
                    Log.d(TAG, ""+sp);
                    String content = MySmsReceiver.sp.getString("contentSms","");
                    Log.d("content", ""+ content);
                }
                else{
                    Toast.makeText(context, "No test", Toast.LENGTH_LONG).show();
                }

            }
        }
    }
    /*
     * Gets a SharedPreferences instance that points to the default file that is
     * used by the preference framework in the given context.
     *
     * @param context The context of the preferences whose values are wanted.
     * @return A SharedPreferences instance that can be used to retrieve and
     * listen to values of the preferences.
     */
   /* public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return context.getSharedPreferences(getDefaultSharedPreferencesName(context),
                Integer.parseInt(getDefaultSharedPreferencesName(context)));
    }

    private static String getDefaultSharedPreferencesName(Context context) {
        return context.getPackageName() + "_preferences";
    }*/
}