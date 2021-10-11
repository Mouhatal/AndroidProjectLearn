package gaaynako.transpay.crudandroidretrofit.rest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("read.php")
    Call<JsonArray> read();

    @POST("create.php")
    Call<JsonObject> create(@Body JsonObject personne);

    @POST("update.php")
    Call<JsonObject> update(@Body JsonObject personne);

    @POST("delete.php")
    Call<JsonObject> delete(@Body JsonObject idPersonne) ;
}
