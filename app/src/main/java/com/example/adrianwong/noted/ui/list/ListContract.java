package com.example.adrianwong.noted.ui.list;

public interface ListContract {

    interface ViewActions {
        void onAddFabClick();
    }

    interface ListView {
        void startAddActivity();
        void startDetailActivity();
    }
}
