package com.example.adrianwong.noted.data.remote;

import com.example.adrianwong.noted.datamodel.remote.UserDataModel;

import retrofit2.Call;

public class RepositoryDataSourceImpl implements RepositoryDataSourceInterface {

    private NotedRestAdapter restAdapter;

    public RepositoryDataSourceImpl(NotedRestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    @Override
    public Call<UserDataModel> loginUser(UserDataModel user) {
        return restAdapter.loginUser(user);
    }

    @Override
    public Call<UserDataModel> registerUser(UserDataModel user) {
        return restAdapter.registerUser(user);
    }

}
