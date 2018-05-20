package com.example.adrianwong.noted.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.adrianwong.noted.data.NotesRepository;

public class AddViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final NotesRepository mRepository;
    private final int mNoteId;

    public AddViewModelFactory(NotesRepository repository, int noteId) {
        this.mRepository = repository;
        this.mNoteId = noteId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddViewModel(mRepository, mNoteId);
    }
}
