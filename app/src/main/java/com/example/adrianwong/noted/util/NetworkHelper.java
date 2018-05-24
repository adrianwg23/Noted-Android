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




    private NetworkHelper() {
    }



}
