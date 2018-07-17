package com.gabrielaangebrandt.pregnancyapp.base;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestUtils {
    public static String URL = "https://b7d270df.ngrok.io/";
    public static RestInterface API;

    public static RestInterface getAPI(){
        if (API == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(URL).build();

            API = retrofit.create(RestInterface.class);
        }
        return API;
    }
}
