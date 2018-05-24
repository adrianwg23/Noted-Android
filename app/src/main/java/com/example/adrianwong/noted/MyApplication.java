package com.example.adrianwong.noted;

import android.app.Application;

import com.example.adrianwong.noted.di.DaggerDataComponent;
import com.example.adrianwong.noted.di.DataComponent;
import com.example.adrianwong.noted.di.DataModule;

public class MyApplication extends Application {

    private DataComponent dataComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        dataComponent = DaggerDataComponent
                .builder()
                .dataModule(new DataModule(this))
                .build();
    }

    public DataComponent getDataComponent() {
        return dataComponent;
    }
}
