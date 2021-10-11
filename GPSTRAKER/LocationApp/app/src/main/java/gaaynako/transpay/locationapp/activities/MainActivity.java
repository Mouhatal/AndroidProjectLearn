package gaaynako.transpay.locationapp.activities;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_PHONE_STATE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import gaaynako.transpay.locationapp.R;
import gaaynako.transpay.locationapp.models.Terminal;
import gaaynako.transpay.locationapp.services.LocationTrack;

public class MainActivity extends AppCompatActivity{

    public static String imei = "";

    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;
    Terminal terminal;

    private Button btnAdd;
    private EditText edtName;

    TelephonyManager telephonyManager;

    public static double latitude  ;
    public static double longitude  ;
    GPSTracker gps;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       edtName = (EditText) findViewById(R.id.edtName);
        btnAdd = (Button) findViewById(R.id.btnRead);

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        gps = new GPSTracker(MainActivity.this);

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Terminal");

        // initializing our object
        // class variable.
        terminal = new Terminal();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                if (telephonyManager != null) {

                    Log.e("Telephone", "IMEI : " + telephonyManager.getDeviceId() );

                    imei = telephonyManager.getDeviceId();


                }
                if(gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Votre Position GPS - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                } else {
                    // Can't get location.
                    // GPS or network is not enabled.
                    // Ask user to enable GPS/network in settings.
                    gps.showSettingsAlert();
                }
                if (TextUtils.isEmpty(name)){
                    String error = "champ requis";
                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                }else {
                    addDatatoFirebase(name,imei,latitude,longitude);
                }
            }
        });




    }*/
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       firebaseDatabase = FirebaseDatabase.getInstance();

       // below line is used to get reference for our database.
       databaseReference = firebaseDatabase.getReference("Terminal");

       // initializing our object
       // class variable.
       terminal = new Terminal();

       permissions.add(ACCESS_FINE_LOCATION);
       permissions.add(ACCESS_COARSE_LOCATION);
       permissions.add(READ_PHONE_STATE);
       permissions.add(ACCESS_NETWORK_STATE);
       permissions.add(INTERNET);
       telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
       permissionsToRequest = findUnAskedPermissions(permissions);
       //get the permissions we have asked for before but are not granted..
       //we will store this in a global list to access later.


       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


           if (permissionsToRequest.size() > 0)
               requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
       }


       Button btn = (Button) findViewById(R.id.btn);


       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               locationTrack = new LocationTrack(MainActivity.this);
               if (telephonyManager != null) {

                   Log.e("Telephone", "IMEI : " + telephonyManager.getDeviceId() );

                   imei = telephonyManager.getDeviceId();


               }

               if (locationTrack.canGetLocation()) {
                 longitude = locationTrack.getLongitude();
                 latitude = locationTrack.getLatitude();

                 Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) +
                                                            "\nLatitude:" + Double.toString(latitude) +
                                                            "\nImei:" + imei, Toast.LENGTH_SHORT).show();
               } else {

                   locationTrack.showSettingsAlert();
               }
               addDatatoFirebase(imei,latitude,longitude);
           }
       });

   }


    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();

        for (Object perm : wanted) {
            if (!hasPermission((String) perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (Object perms : permissionsToRequest) {
                    if (!hasPermission((String) perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale((String) permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    private void addDatatoFirebase(String imei, double latitude, double longitude) {
        // below 3 lines of code is used to set
        // data in our object class.
        terminal.setImei(imei);
        terminal.setLatitude(latitude);
        terminal.setLongitude(longitude);


        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.setValue(terminal);

                // after adding this data we are showing toast message.
                Toast.makeText(MainActivity.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(MainActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTrack.stopListener();
    }

}