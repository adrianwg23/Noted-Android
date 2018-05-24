package com.example.adrianwong.noted.ui.login;

import android.content.SharedPreferences.Editor;

import com.example.adrianwong.noted.data.remote.UserRepository;
import com.example.adrianwong.noted.datamodel.ResponseDataModel;
import com.example.adrianwong.noted.ui.base.BasePresenter;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class LoginPresenter extends BasePresenter<LoginContract.LoginView> implements LoginContract.ViewActions {

    private UserRepository dataSource;
    private CompositeDisposable disposable;
    private Editor editor;

    @Inject
    public LoginPresenter(UserRepository dataSource, Editor editor, CompositeDisposable disposable) {
        this.dataSource = dataSource;
        this.disposable = disposable;
        this.editor = editor;
    }

    @Override
    public void onLoginClicked(String username, String password) {
        loginOrRegister(dataSource.loginUser(new ResponseDataModel(username, password)), username);
    }

    @Override
    public void onRegisterClicked(String username, String password) {
        loginOrRegister(dataSource.registerUser(new ResponseDataModel(username, password)), username);
    }

    @Override
    public void onStop() {
        disposable.clear();
    }

    private void loginOrRegister(Observable<Response<ResponseDataModel>> observable, final String username) {
        mView.showLoading();
        disposable.add(observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<Response<ResponseDataModel>>() {
                    @Override
                    public void onNext(Response<ResponseDataModel> response) {
                        if (response.isSuccessful()) {
                            String accessToken = response.body().getAccessToken();
                            String refreshToken = response.body().getRefreshToken();
                            int userId = response.body().getUserId();

                            editor.putString("access_token", "Bearer " + accessToken);
                            editor.putString("refresh_token", "Bearer " + refreshToken);
                            editor.putString("username", username);
                            editor.putInt("user_id", userId);
                            editor.putBoolean("is_logged_in", true);
                            editor.commit();

                            mView.startListActivity();
                        }
                        if (response.errorBody() != null) {
                            Moshi moshi = new Moshi.Builder().build();
                            JsonAdapter<ResponseDataModel> jsonAdapter = moshi.adapter(ResponseDataModel.class);

                            try {
                                String message = jsonAdapter.fromJson(response.errorBody().string()).getMessage();
                                mView.showToast(message);
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
}
