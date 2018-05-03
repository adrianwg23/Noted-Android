package com.example.adrianwong.noted.activity.login;

import android.arch.lifecycle.LifecycleObserver;
import android.text.TextUtils;

import com.example.adrianwong.noted.data.remote.RepositoryDataSourceInterface;
import com.example.adrianwong.noted.datamodel.remote.UserDataModel;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class LoginPresenter implements LifecycleObserver {

    private LoginView view;
    private RepositoryDataSourceInterface dataSource;
    private CompositeDisposable disposable;

    public LoginPresenter(LoginView view, RepositoryDataSourceInterface dataSource,
                          CompositeDisposable disposable) {
        this.view = view;
        this.dataSource = dataSource;
        this.disposable = disposable;
    }

    public void login(String username, String password) {
        if (areFieldsEmpty(username, password)) {
            view.makeToast("Please enter a valid username and/or password");
            return;
        }
        loginOrRegister(dataSource.loginUser(new UserDataModel(username, password)));
    }

    public void register(String username, String password) {
        if (areFieldsEmpty(username, password)) {
            view.makeToast("Please enter a valid username and/or password");
            return;
        }

        loginOrRegister(dataSource.registerUser(new UserDataModel(username, password)));
    }

    private void loginOrRegister(Observable<Response<UserDataModel>> observable) {
        disposable.add(observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<Response<UserDataModel>>() {
                    @Override
                    public void onNext(Response<UserDataModel> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            if (!TextUtils.isEmpty(message)){
                                view.makeToast(response.body().getMessage());
                            }
                        }
                        if (response.errorBody() != null) {
                            Moshi moshi = new Moshi.Builder().build();
                            JsonAdapter<UserDataModel> jsonAdapter = moshi.adapter(UserDataModel.class);

                            try {
                                String message = jsonAdapter.fromJson(response.errorBody().string()).getMessage();
                                view.makeToast(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })

        );
    }

    private boolean areFieldsEmpty(String username, String password) {
        return TextUtils.isEmpty(username) || TextUtils.isEmpty(password);
    }
}
