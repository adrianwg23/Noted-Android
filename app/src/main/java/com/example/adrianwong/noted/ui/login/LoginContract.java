package com.example.adrianwong.noted.ui.login;

public interface LoginContract {

    interface ViewActions {
        void onLoginClicked(String username, String password);
        void onRegisterClicked(String username, String password);
        void onStop();
    }

    interface LoginView {
        void showToast(String message);
        void showLoading();
        void startListActivity();
    }
}
