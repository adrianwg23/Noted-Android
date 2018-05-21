package com.example.adrianwong.noted.data.remote;

import com.example.adrianwong.noted.datamodel.ResponseDataModel;

import io.reactivex.Observable;
import retrofit2.Response;

public class UserRepositoryImpl implements UserRepository {

    private NotedRestAdapter restAdapter;

    public UserRepositoryImpl(NotedRestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    @Override
    public Observable<Response<ResponseDataModel>> loginUser(ResponseDataModel user) {
        return restAdapter.loginUser(user);
    }

    @Override
    public Observable<Response<ResponseDataModel>> registerUser(ResponseDataModel user) {
        return restAdapter.registerUser(user);
    }

}
