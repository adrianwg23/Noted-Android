package com.example.adrianwong.noted.data.remote;

import com.example.adrianwong.noted.datamodel.UserDataModel;

import io.reactivex.Observable;
import retrofit2.Response;

public interface UserRepository {

    Observable<Response<UserDataModel>> loginUser (UserDataModel user);
    Observable<Response<UserDataModel>> registerUser (UserDataModel user);
}
