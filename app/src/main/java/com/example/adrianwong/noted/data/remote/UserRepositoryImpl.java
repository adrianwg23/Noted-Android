package com.example.adrianwong.noted.data.remote;

import com.example.adrianwong.noted.datamodel.UserDataModel;

import io.reactivex.Observable;
import retrofit2.Response;

public class UserRepositoryImpl implements UserRepository {

    private NotedRestAdapter restAdapter;

    public UserRepositoryImpl(NotedRestAdapter restAdapter) {
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
