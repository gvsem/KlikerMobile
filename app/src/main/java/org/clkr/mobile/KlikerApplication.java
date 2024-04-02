package org.clkr.mobile;

import android.app.Application;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KlikerApplication extends Application {

    public Retrofit api = null;
    public String accessToken = null;

    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request newRequest  = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .build();
            return chain.proceed(newRequest);
        }).build();

        this.api = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://clkr.me/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
}
