package com.example.adrianwong.noted.di;

import android.app.Application;

import com.example.adrianwong.noted.data.NotesRepository;
import com.example.adrianwong.noted.data.local.AppDatabase;
import com.example.adrianwong.noted.data.remote.NotedRestAdapter;
import com.example.adrianwong.noted.data.remote.UrlManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class DataModule {

    Application application;

    public DataModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit (OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(UrlManager.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient (HttpLoggingInterceptor loggingInterceptor) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        return client;
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    NotedRestAdapter provideRestAdapter(Retrofit retrofit) {
        return new NotedRestAdapter(retrofit);
    }

    @Provides
    @Singleton
    NotesRepository provideRepository(NotedRestAdapter restAdapter) {
        AppDatabase database = AppDatabase.getInstance(application.getApplicationContext());
        return NotesRepository.getInstance(database.noteDao(), restAdapter);
    }
}
