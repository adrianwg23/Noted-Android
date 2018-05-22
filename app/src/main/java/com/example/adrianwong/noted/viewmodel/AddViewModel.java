package com.example.adrianwong.noted.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.adrianwong.noted.data.NotesRepository;
import com.example.adrianwong.noted.datamodel.NoteItem;
import com.example.adrianwong.noted.util.PresenterHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AddViewModel extends ViewModel {

    CompositeDisposable mDisposable;
    private final NotesRepository mRepository;
    private int mNoteId;

    public AddViewModel(NotesRepository repository, int noteId) {
        mRepository = repository;
        mNoteId = noteId;
        mDisposable = PresenterHelper.getDisposable();
    }

    public LiveData<NoteItem> getNote() { return mRepository.getNote(mNoteId); }

    public void insertNote(NoteItem noteItem) { mRepository.insertNote(noteItem); }

    public void insertRemoteNote(String accessToken, NoteItem noteItem) {
        mDisposable.add(mRepository.newRemoteNote(accessToken, noteItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }

    public void updateRemoteNote(String accessToken, NoteItem noteItem) {
        mDisposable.add(mRepository.updateRemoteNote(accessToken, noteItem, noteItem.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }

    public void updateNote(NoteItem noteItem) { mRepository.updateNote(noteItem); }

    public void onStop() {
        mDisposable.clear();
    }
}
