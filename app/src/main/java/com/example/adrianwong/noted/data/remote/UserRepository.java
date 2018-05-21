package com.example.adrianwong.noted.data.remote;

import com.example.adrianwong.noted.datamodel.ResponseDataModel;

import io.reactivex.Observable;
import retrofit2.Response;

public interface UserRepository {

    Observable<Response<ResponseDataModel>> loginUser (ResponseDataModel user);
    Observable<Response<ResponseDataModel>> registerUser (ResponseDataModel user);
}
