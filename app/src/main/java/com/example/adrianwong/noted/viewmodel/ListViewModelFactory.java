package com.example.adrianwong.noted.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.adrianwong.noted.data.NotesRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final NotesRepository mRepository;
    private final CompositeDisposable mDisposable;

    @Inject
    public ListViewModelFactory(NotesRepository repository, CompositeDisposable disposable) {
        this.mRepository = repository;
        this.mDisposable = disposable;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ListViewModel(mRepository, mDisposable);
    }
}
