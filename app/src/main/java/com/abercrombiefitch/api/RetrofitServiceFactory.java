package com.abercrombiefitch.api;

import android.content.Context;

import com.abercrombiefitch.api.model.JacksonConverter;
import com.abercrombiefitch.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Nirajan on 9/28/2015.
 */
public class RetrofitServiceFactory {
    private static final String TAG = "RetrofitServiceFactory";

    private static final String BASE_URL = "http://www.abercrombie.com/anf/nativeapp/";



    public static AbercrombieAPI createRetrofitService(Context context) {
        File cacheDir = new File(context.getCacheDir().getAbsolutePath(), "jsonCache");
        Log.i(TAG, "Cache Directory: " + context.getCacheDir().getAbsolutePath().toString());
        Cache cache = null;
        try {
            cache = new Cache(cacheDir, 10*1024*1024); // 10 MiB

        } catch (IOException e) {
            e.printStackTrace();
        }

        OkHttpClient okHttpClient = new OkHttpClient();
        if (cache != null) {
            okHttpClient.setCache(cache);
        }

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                request.addHeader("Accept", "application/json");
            }
        };
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setConverter(new JacksonConverter(new ObjectMapper()))
                .setEndpoint(BASE_URL)
                .setClient(new OkClient(okHttpClient))
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        final AbercrombieAPI abercrombieAPI = restAdapter.create(AbercrombieAPI.class);
        return abercrombieAPI;
    }

}
