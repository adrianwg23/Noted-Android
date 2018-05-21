package com.example.adrianwong.noted.ui.list;

public interface ListContract {

    interface ViewActions {
        void onAddFabClick();
        void onStop();
    }

    interface ListView {
        void startAddActivity();
        void initViews();
        void setupViewModel();
        void showUndoSnackBar();
    }
}
