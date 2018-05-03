package com.example.adrianwong.noted.activity.login;

import android.arch.lifecycle.LifecycleObserver;
import android.util.Log;

import com.example.adrianwong.noted.data.remote.RepositoryDataSourceInterface;
import com.example.adrianwong.noted.datamodel.remote.UserDataModel;
import com.example.adrianwong.noted.util.BaseSchedulerProvider;
import com.example.adrianwong.noted.util.NetworkHelper;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LifecycleObserver {

    private LoginView view;
    private RepositoryDataSourceInterface dataSource;
    private CompositeDisposable disposable;
    private BaseSchedulerProvider scheduler;

    public LoginPresenter(LoginView view, RepositoryDataSourceInterface dataSource,
                          CompositeDisposable disposable) {
        this.view = view;
        this.dataSource = dataSource;
        this.disposable = disposable;
    }

    public void loginOrRegister(String username, String password, final int requestId) {
        UserDataModel user = new UserDataModel(username, password);
        Call<UserDataModel> call;

        if (requestId == NetworkHelper.LOGIN) {
            call = dataSource.loginUser(user);
        } else {
            call = dataSource.registerUser(user);
        }

        call.enqueue(new Callback<UserDataModel>() {
            @Override
            public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                if (response.isSuccessful()) {
                    if (requestId == NetworkHelper.REGISTER) view.toastMessage(response.body().getMessage());
                    if (requestId == NetworkHelper.LOGIN) {
                        Log.d("LoginPresenter", "access token: " + response.body().getAccessToken());
                        Log.d("LoginPresenter", "refresh token: " + response.body().getRefreshToken());
                    }
                } else {
                    if (response.errorBody() != null) {
                        Moshi moshi = new Moshi.Builder().build();
                        JsonAdapter<UserDataModel> jsonAdapter = moshi.adapter(UserDataModel.class);

                        try {
                            String message = jsonAdapter.fromJson(response.errorBody().string()).getMessage();
                            view.toastMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDataModel> call, Throwable t) {
                view.toastMessage("Something went wrong :(");
            }
        });

    }
}
