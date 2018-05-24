package com.example.adrianwong.noted.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.adrianwong.noted.data.NotesRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class AddViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final CompositeDisposable mDisposable;
    private final NotesRepository mRepository;

    @Inject
    public AddViewModelFactory(NotesRepository repository, CompositeDisposable disposable) {
        this.mRepository = repository;
        this.mDisposable = disposable;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddViewModel(mRepository, mDisposable);
    }
}
