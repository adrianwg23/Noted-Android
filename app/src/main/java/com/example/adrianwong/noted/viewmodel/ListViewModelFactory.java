package com.example.adrianwong.noted.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.adrianwong.noted.data.NotesRepository;

public class ListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final NotesRepository mRepository;

    public ListViewModelFactory(NotesRepository repository) {
        this.mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ListViewModel(mRepository);
    }
}
