package com.example.adrianwong.noted.ui.add;

import android.content.DialogInterface;

public interface AddContract {

    interface ViewActions {
        void onStop();
        void onNoteSaved(String noteTitle, String noteBody, int priorityColourId);
    }

    interface AddView {
        void initViews();
        void setupViewModel();
        void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener);
        void onBackPressed();
        boolean hasNoteChanged();
    }
}
