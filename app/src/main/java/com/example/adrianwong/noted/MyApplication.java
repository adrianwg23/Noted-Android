package com.example.adrianwong.noted;

import android.app.Application;

import com.example.adrianwong.noted.di.AppModule;
import com.example.adrianwong.noted.di.DaggerAppComponent;
import com.example.adrianwong.noted.di.AppComponent;
import com.example.adrianwong.noted.di.DataModule;

public class MyApplication extends Application {

    private static MyApplication app;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

        appComponent = DaggerAppComponent
                .builder()
                .dataModule(new DataModule(this))
                .appModule(new AppModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public static MyApplication getApp() {
        return app;
    }
}
