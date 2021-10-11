package gaaynako.transpay.androidretrofit.rest;


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("connexion")
    Call<JsonObject> authentification(@Query("login") String login,
                                      @Query("datedeb") String datedebut
                                     );
    @Headers("Content-Type:application/json")
    @POST("add_product")
    Call<JsonObject> addProduct(@Body JsonObject product);

    @Headers("Content-Type:application/json")
    @POST("sync_produit")
    Call<JsonObject> synchroniserProduct(@Body JsonObject product);
}
