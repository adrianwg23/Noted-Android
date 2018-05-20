package com.example.adrianwong.noted.ui.add;

public interface AddContract {

    interface ViewActions {
        void onStop();
        void onNoteSaved(String noteTitle, String noteBody, int priorityColourId);
    }

    interface AddView {
        void initViews();
    }
}
