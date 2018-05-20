package com.example.adrianwong.noted.util;

import com.example.adrianwong.noted.data.remote.UserRepositoryImpl;

import io.reactivex.disposables.CompositeDisposable;

public class PresenterHelper {

    private static final UserRepositoryImpl dataSource = new UserRepositoryImpl(NetworkHelper.getRestAdapter());
    private static final CompositeDisposable disposable = new CompositeDisposable();

    private PresenterHelper() {
    }

    public static UserRepositoryImpl getDataSource() {
        return dataSource;
    }

    public static CompositeDisposable getDisposable() {
        return disposable;
    }
}
