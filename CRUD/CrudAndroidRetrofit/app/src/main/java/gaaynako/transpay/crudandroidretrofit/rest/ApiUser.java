package gaaynako.transpay.crudandroidretrofit.rest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUser {
    public static final String BASE_URL = "http://6445-154-124-160-86.ngrok.io/api/";
    private static Retrofit retrofit = null;


    public static OkHttpClient getClient(){

        return new OkHttpClient.Builder().addInterceptor(
                new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(4, TimeUnit.MINUTES)
                .readTimeout(4,TimeUnit.MINUTES)
                .build();
    }

    public static Retrofit getRetrofit() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .client(getClient())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
