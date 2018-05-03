package com.example.adrianwong.noted.data.remote;

import com.example.adrianwong.noted.datamodel.remote.UserDataModel;

import retrofit2.Call;

public class RepositoryDataSourceImpl implements RepositoryDataSourceInterface {

    private NotedRestAdapter restAdapter;

    public RepositoryDataSourceImpl(NotedRestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }


    @Override
    public Call<UserDataModel> loginUser(String username, String password) {
        return restAdapter.loginUser(new UserDataModel(username, password));
    }

    @Override
    public Call<UserDataModel> registerUser(String username, String password) {
        return restAdapter.registerUser(new UserDataModel(username, password));
    }

}
