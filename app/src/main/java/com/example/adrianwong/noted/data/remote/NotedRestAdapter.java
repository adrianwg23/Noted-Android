package com.example.adrianwong.noted.data.remote;


import com.example.adrianwong.noted.datamodel.remote.UserDataModel;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class NotedRestAdapter {

    private final NotedService notedService;

    public NotedRestAdapter(Retrofit retrofit) {
        this.notedService = retrofit.create(NotedService.class);
    }

    public interface NotedService {

        @POST(UrlManager.LOGIN)
        Observable<Response<UserDataModel>> loginUser(@Body UserDataModel user);

        @POST(UrlManager.REGISTER)
        Observable<Response<UserDataModel>> registerUser(@Body UserDataModel user);
    }

    public Observable<Response<UserDataModel>> loginUser(UserDataModel user) {
        return notedService.loginUser(user);
    }

    public Observable<Response<UserDataModel>> registerUser(UserDataModel user) {
        return notedService.registerUser(user);
    }
}
