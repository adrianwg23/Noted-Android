package com.example.adrianwong.noted.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.adrianwong.noted.data.NotesRepository;
import com.example.adrianwong.noted.datamodel.NoteItem;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AddViewModel extends ViewModel {

    private final CompositeDisposable mDisposable;
    private final NotesRepository mRepository;

    public AddViewModel(NotesRepository repository, CompositeDisposable disposable) {
        mRepository = repository;
        mDisposable = disposable;
    }

    /**
     * Called when loading a note that already exists in the db
     * @param noteId
     * @return
     */
    public LiveData<NoteItem> getNote(int noteId) { return mRepository.getNote(noteId); }

    /**
     * Insertion of note to local db
     * @param noteItem
     */
    public void insertNote(NoteItem noteItem) { mRepository.insertNote(noteItem); }

    /**
     * insertion of note to remote db
     * @param accessToken
     * @param noteItem
     */
    public void insertRemoteNote(String accessToken, NoteItem noteItem) {
        mDisposable.add(mRepository.newRemoteNote(accessToken, noteItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }

    /**
     * updating of note in remote db
     * @param accessToken
     * @param noteItem
     */
    public void updateRemoteNote(String accessToken, NoteItem noteItem) {
        mDisposable.add(mRepository.updateRemoteNote(accessToken, noteItem, noteItem.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }

    /**
     * updating of note in local db
     * @param noteItem
     */
    public void updateNote(NoteItem noteItem) { mRepository.updateNote(noteItem); }

    public void onStop() {
        mDisposable.clear();
    }
}
