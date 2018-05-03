package com.example.adrianwong.noted.data.remote;

import com.example.adrianwong.noted.datamodel.remote.UserDataModel;

import retrofit2.Call;

public interface RepositoryDataSourceInterface {

    Call<UserDataModel> loginUser (String username, String password);
    Call<UserDataModel> registerUser (String username, String password);
}
