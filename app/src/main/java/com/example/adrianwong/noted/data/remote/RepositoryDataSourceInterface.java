package com.example.adrianwong.noted.data.remote;

import com.example.adrianwong.noted.datamodel.remote.UserDataModel;

import retrofit2.Call;

public interface RepositoryDataSourceInterface {

    Call<UserDataModel> loginUser (UserDataModel user);
    Call<UserDataModel> registerUser (UserDataModel user);
}
