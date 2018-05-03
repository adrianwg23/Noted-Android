package com.example.adrianwong.noted.util;

import com.example.adrianwong.noted.data.remote.RepositoryDataSourceImpl;

import io.reactivex.disposables.CompositeDisposable;

public class PresenterHelper {

    private static final RepositoryDataSourceImpl dataSource = new RepositoryDataSourceImpl(NetworkHelper.getRestAdapter());
    private static final CompositeDisposable disposable = new CompositeDisposable();

    private PresenterHelper() {
    }

    public static RepositoryDataSourceImpl getDataSource() {
        return dataSource;
    }

    public static CompositeDisposable getDisposable() {
        return disposable;
    }
}
