package gaaynako.transpay.crudandroidretrofit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;

import gaaynako.transpay.crudandroidretrofit.R;
import gaaynako.transpay.crudandroidretrofit.config.MySmsReceiver;
import gaaynako.transpay.crudandroidretrofit.models.Personne;
import gaaynako.transpay.crudandroidretrofit.rest.ApiInterface;
import gaaynako.transpay.crudandroidretrofit.rest.ApiUser;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnNext;
    private Realm realm;
    GpsTracker gps;
    public static double latitude  ;
    public static double longitude  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNext = findViewById(R.id.btnNext);
        gps = new GpsTracker(MainActivity.this);

        // Check if GPS enabled
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


        realm = Realm.getDefaultInstance();

        ApiInterface apiService = ApiUser.getRetrofit().create(ApiInterface.class);
        apiService.read().enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray resultat = response.body();

                if (resultat != null){
                    for (JsonElement j : resultat){
                        JsonObject personne = j.getAsJsonObject();
                        //Log.e("personnes", "" + personne);
                        Personne p = new Personne();
                        // on below line we are getting id for the course which we are storing.
                        Number id = realm.where(Personne.class).max("id");

                        // on below line we are
                        // creating a variable for our id.
                        long nextId;

                        // validating if id is null or not.
                        if (id == null) {
                            // if id is null
                            // we are passing it as 1.
                            nextId = 1;
                        } else {
                            // if id is not null then
                            // we are incrementing it by 1
                            nextId = id.intValue() + 1;
                        }
                        // on below line we are setting the
                        // data entered by user in our modal class.
                        p.setId((int) nextId);
                        p.setIdPersonne((personne.get("IdPersonne").getAsInt()));
                        p.setAdressePersonne(personne.get("AdressePersonne").getAsString());
                        p.setEmailPersonne(personne.get("EmailPersonne").getAsString());
                        p.setNomPrenomPersonne(personne.get("NomPrenomPersonne").getAsString());
                        p.setTelPersonne(personne.get("TelPersonne").getAsString());
                        p.setDateNaissancePersonne(personne.get("DateNaissancePersonne").getAsString());
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                // inside on execute method we are calling a method
                                // to copy to real m database from our modal class.
                                realm.copyToRealm(p);
                            }
                        });
                        Log.e("personne", "" + p);

                    }
                }else {
                    Toast.makeText(getApplicationContext(), "chargement des données en cours", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,AddPersonneActivity.class);
                startActivity(i);
            }
        });

    }

}