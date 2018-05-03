package com.example.adrianwong.noted.data.remote;

import android.util.Log;

import com.example.adrianwong.noted.datamodel.remote.UserDataModel;

import io.reactivex.Flowable;
import retrofit2.Call;
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
        Call<UserDataModel> loginUser(@Body UserDataModel user);

        @POST(UrlManager.REGISTER)
        Call<UserDataModel> registerUser(@Body UserDataModel user);
    }

    public Call<UserDataModel> loginUser(UserDataModel user) {
        return notedService.loginUser(user);
    }

    public Call<UserDataModel> registerUser(UserDataModel user) {
        return notedService.registerUser(user);
    }
}
