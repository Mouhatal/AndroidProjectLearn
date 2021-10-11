package gaaynako.transpay.crudandroidretrofit.service;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

import gaaynako.transpay.crudandroidretrofit.models.Personne;
import gaaynako.transpay.crudandroidretrofit.rest.ApiInterface;
import gaaynako.transpay.crudandroidretrofit.rest.ApiUser;
import io.realm.Realm;
import io.realm.RealmQuery;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonneService extends Service {

    private Realm realm;

    private List listRequest;
    private Personne personne;
    private String imei = "";
    private RealmQuery<Personne> personnes;

    private Gson gson;
    public static final int SERVICE_ID = 1;

    public PersonneService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

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

                        personnes = realm.where(Personne.class).equalTo("idPersonne",personne.get("IdPersonne").getAsInt());
                   /*/for (Personne personne1 : personnes) {
                        if (personne1.getIdPersonne() == personne.get("IdPersonne").getAsInt()){

                        }else {

                        }
                    }*/
                        Log.e("personne", "" + personnes);

                        if (personnes == null)
                        {
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    // inside on execute method we are calling a method
                                    // to copy to real m database from our modal class.
                                    realm.copyToRealm(p);
                                }
                            });
                        }
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "chargement des données en cours", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
