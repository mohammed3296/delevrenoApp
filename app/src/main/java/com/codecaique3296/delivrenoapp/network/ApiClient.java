package com.codecaique3296.delivrenoapp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //http://www.codecaique.com/graduation_project/AOU/code_caique_t1_api/
    public static final String BASE_URL = "http://192.168.1.7/xampp/DelvernoAppAPI/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
