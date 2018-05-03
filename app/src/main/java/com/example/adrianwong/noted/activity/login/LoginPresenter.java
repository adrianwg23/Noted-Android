package com.example.adrianwong.noted.activity.login;

import android.arch.lifecycle.LifecycleObserver;
import android.util.Log;
import android.widget.Toast;

import com.example.adrianwong.noted.data.remote.NotedRestAdapter;
import com.example.adrianwong.noted.data.remote.RepositoryDataSourceInterface;
import com.example.adrianwong.noted.datamodel.remote.UserDataModel;
import com.example.adrianwong.noted.util.BaseSchedulerProvider;

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

    public void login(String username, String password) {
        Call<UserDataModel> call = dataSource.loginUser(username, password);

        call.enqueue(new Callback<UserDataModel>() {
            @Override
            public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                if (response.isSuccessful()){
                    view.toastMessage(response.body().getAccessToken());
                    Log.d("LoginPresenter", response.body().getMessage());
                } else {
                    Log.d("LoginPresenter", response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<UserDataModel> call, Throwable t) {
                view.toastMessage("Something went wrong :(");
            }
        });
    }

    public void register(String username, String password) {
        Call<UserDataModel> call = dataSource.registerUser(username, password);

        call.enqueue(new Callback<UserDataModel>() {
            @Override
            public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                Log.d("LoginPresenter", response.body().getMessage());
            }

            @Override
            public void onFailure(Call<UserDataModel> call, Throwable t) {
                view.toastMessage("Something went wrong");
                Log.wtf("LoginRegister", t.toString());
            }
        });
    }
}
