package com.example.adrianwong.noted.data.remote;

import com.example.adrianwong.noted.datamodel.remote.UserDataModel;

import io.reactivex.Observable;
import retrofit2.Response;

public class RepositoryDataSourceImpl implements RepositoryDataSourceInterface {

    private NotedRestAdapter restAdapter;

    public RepositoryDataSourceImpl(NotedRestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    @Override
    public Observable<Response<UserDataModel>> loginUser(UserDataModel user) {
        return restAdapter.loginUser(user);
    }

    @Override
    public Observable<Response<UserDataModel>> registerUser(UserDataModel user) {
        return restAdapter.registerUser(user);
    }

}
