package gaaynako.transpay.crudandroidretrofit.config;

import android.content.Context;
import android.widget.Toast;

public class SmsManager {

    public static String Tel = "761911376" +
            "";

    public static  void sendSMSDirect(Context context, String message, String phoneNumber) {
        try {
            android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            //Toast.makeText(context, "SMS TICKET ENVOYÉ...", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(context,ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public static String formatAjout(String nom, String telephone , String email ){
        String sms = "** CRUD - RETROFIT **" ;
        sms += "\n    Ajout d'un contact" ;
        sms += "\nNom: "+nom ;
        sms += "\nTelephone: "+telephone ;
        sms += "\nEmail: "+email ;
        return sms ;
    }

    public static String formatSuppression(String nom, String telephone , String email ){
        String sms = "** CRUD - RETROFIT **" ;
        sms += "\n    Ajout d'un contact" ;
        sms += "\nNom: "+nom ;
        sms += "\nTelephone: "+telephone ;
        sms += "\nEmail: "+email ;
        return sms ;
    }
}
