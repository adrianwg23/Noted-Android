package com.example.adrianwong.noted.ui.add;

import android.content.DialogInterface;

public interface AddContract {

    interface AddView {
        void initViews();
        void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener);
        void onBackPressed();
        boolean hasNoteChanged();
    }
}
