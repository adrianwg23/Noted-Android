package com.example.adrianwong.noted.util;

import com.example.adrianwong.noted.data.remote.NotedRestAdapter;
import com.example.adrianwong.noted.data.remote.UrlManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class NetworkHelper {

    public static final int LOGIN = 0;
    public static final int REGISTER = 1;

    private static final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(getInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build();

    private static final Retrofit retrofit = new Retrofit.Builder()
            .client(getClient())
            .baseUrl(UrlManager.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    private static final NotedRestAdapter restAdapter = new NotedRestAdapter(getRetrofit());

    private NetworkHelper() {
    }

    private static HttpLoggingInterceptor getInterceptor() {
        return interceptor;
    }

    private static OkHttpClient getClient() {
        return client;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static NotedRestAdapter getRestAdapter() {
        return restAdapter;
    }
}
