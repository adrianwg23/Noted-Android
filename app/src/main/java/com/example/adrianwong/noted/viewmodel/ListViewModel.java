package com.example.adrianwong.noted.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.adrianwong.noted.data.NotesRepository;
import com.example.adrianwong.noted.datamodel.NoteItem;
import com.example.adrianwong.noted.datamodel.ResponseDataModel;
import com.example.adrianwong.noted.util.PresenterHelper;

import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {

    private final NotesRepository mRepository;
    private final CompositeDisposable mDisposable;

    private NoteItem tempNoteItem;

    public ListViewModel(NotesRepository repository) {
        this.mRepository = repository;
        this.mDisposable = PresenterHelper.getDisposable();
    }

    public LiveData<List<NoteItem>> getNoteList(int id) {
        return mRepository.getNotes(id);
    }

    public void insertMultipleNotes(List<NoteItem> notes) {
        mRepository.insertMultipleNotes(notes);
    }

    public void deleteNote(NoteItem note) {
        mRepository.deleteNote(note);
        tempNoteItem = note;
    }

    public void deleteRemoteNote(String accessToken, int noteId) {
        mDisposable.add(mRepository.deleteRemoteNote(accessToken, noteId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }

    public void onUndoConfirmed() {
        mRepository.insertNote(tempNoteItem);
        tempNoteItem = null;
    }

    public void onUndoConfirmedRemote(String accessToken) {
        mDisposable.add(mRepository.newRemoteNote(accessToken, tempNoteItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }

    public void onSnackBarTimeout() {
        tempNoteItem = null;
    }

    public void onStop() {
        mDisposable.clear();
    }
}
