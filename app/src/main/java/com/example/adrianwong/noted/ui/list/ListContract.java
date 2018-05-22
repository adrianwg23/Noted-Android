package com.example.adrianwong.noted.ui.list;

import com.example.adrianwong.noted.datamodel.NoteItem;

import java.util.List;

public interface ListContract {

    interface ViewActions {
        void onStop();
        void onAddFabClick();
        void fetchNotes(String accessToken, String username);
    }

    interface ListView {
        void initViews();
        void setupViewModel();
        void showUndoSnackBar();
        void startAddActivity();
        void startLoginActivity();
        void insertToDb(List<NoteItem> notes);
        void makeRequest();
    }
}
