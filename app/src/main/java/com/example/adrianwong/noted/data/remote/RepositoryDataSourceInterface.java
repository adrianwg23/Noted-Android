package com.example.adrianwong.noted.data.remote;

import com.example.adrianwong.noted.datamodel.remote.UserDataModel;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;

public interface RepositoryDataSourceInterface {

    Observable<Response<UserDataModel>> loginUser (UserDataModel user);
    Observable<Response<UserDataModel>> registerUser (UserDataModel user);
}
