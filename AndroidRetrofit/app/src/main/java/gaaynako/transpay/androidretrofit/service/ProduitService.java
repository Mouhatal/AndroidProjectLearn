package gaaynako.transpay.androidretrofit.service;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import gaaynako.transpay.androidretrofit.gson.ProduitGson;
import gaaynako.transpay.androidretrofit.models.Produit;
import gaaynako.transpay.androidretrofit.rest.ApiInterface;
import gaaynako.transpay.androidretrofit.rest.ApiUser;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProduitService extends Service {
    private Realm realm;

    private List listRequest;
    private Produit produit;
    private String imei = "";

    private Gson gson;

    SimpleDateFormat dateFormat;

    public static final int SERVICE_ID = 1;

    public ProduitService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        realm = Realm.getDefaultInstance();

        listRequest = new ArrayList();

        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        final RealmResults<Produit> lesproduits = realm.where(Produit.class).findAll();

        if (!lesproduits.isEmpty()) {

            produit = lesproduits.get(lesproduits.size() - 1);

            if (!produit.getStatut()) {

                Log.e("produit non Synchronisée", "" + produit.getStatut());

                ProduitGson produitGson = new ProduitGson();


                produitGson.setName(produit.getName());
                produitGson.setCategory(produit.getCategory());
                produitGson.setCost(produit.getCost());
                produitGson.setCreated_at(produit.getCreated_at());

                gson = new GsonBuilder().create();
                JsonObject jsonObject = gson.toJsonTree(produitGson).getAsJsonObject();

                Log.i("produit ", " " + jsonObject);

                ApiInterface apiService = ApiUser.getRetrofit().create(ApiInterface.class);
                ApiInterface apiServiceP = ApiUser.getRetrofit().create(ApiInterface.class);
                apiServiceP.addProduct(jsonObject).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject produit = response.body();
                        Log.e("Reponse Json","" + produit);
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });

            }

        }


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //manager.cancel(11);

    }
}
