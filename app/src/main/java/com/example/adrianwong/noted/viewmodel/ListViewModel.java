package com.example.adrianwong.noted.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.adrianwong.noted.data.NotesRepository;
import com.example.adrianwong.noted.datamodel.NoteItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {

    private final NotesRepository mRepository;

    private CompositeDisposable mDisposable;
    private NoteItem tempNoteItem;

    public ListViewModel(NotesRepository repository, CompositeDisposable disposable) {
        this.mRepository = repository;
        this.mDisposable = disposable;
    }

    /**
     * Called to populate and react to any changes to the note list (Live Data)
     * @param id
     * @return
     */
    public LiveData<List<NoteItem>> getNoteList(int id) {
        return mRepository.getNotes(id);
    }

    /**
     * Called when the app fetches remote notes after log in
     * @param notes
     */
    public void insertMultipleNotes(List<NoteItem> notes) {
        mRepository.insertMultipleNotes(notes);
    }

    /**
     * delete local note
     * @param note
     */
    public void deleteNote(NoteItem note) {
        mRepository.deleteNote(note);
        tempNoteItem = note;
    }

    /**
     * delete note in remote db
     * @param accessToken
     * @param noteId
     */
    public void deleteRemoteNote(String accessToken, int noteId) {
        mDisposable.add(mRepository.deleteRemoteNote(accessToken, noteId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }

    /**
     * add back note locally
     */
    public void onUndoConfirmed() {
        mRepository.insertNote(tempNoteItem);
        tempNoteItem = null;
    }


    public void onSnackBarTimeout() {
        tempNoteItem = null;
    }

    public void onStop() {
        mDisposable.clear();
    }
}
