package com.example.adrianwong.noted.ui.list;

import android.content.SharedPreferences;

import com.example.adrianwong.noted.data.NotesRepository;
import com.example.adrianwong.noted.datamodel.NoteItem;
import com.example.adrianwong.noted.datamodel.ResponseDataModel;
import com.example.adrianwong.noted.ui.base.BasePresenter;
import com.example.adrianwong.noted.util.PresenterHelper;

import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ListPresenter extends BasePresenter<ListContract.ListView> implements ListContract.ViewActions {

    private NotesRepository mRepository;
    private CompositeDisposable disposable;
    SharedPreferences.Editor editor;

    public ListPresenter(NotesRepository repository, SharedPreferences.Editor editor) {
        this.mRepository = repository;
        this.disposable = PresenterHelper.getDisposable();
        this.editor = editor;
    }


    @Override
    public void onAddFabClick() {
        mView.startAddActivity();
    }

    @Override
    public void fetchNotes(String accessToken, String username) {
        disposable.add(mRepository.fetchNotes(accessToken, username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponseDataModel>() {
                    @Override
                    public void onNext(ResponseDataModel responseDataModel) {
                        List<NoteItem> notes = responseDataModel.getServerNotes();
                        mView.insertToDb(notes);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );
    }

    public void logout(String accessToken, String refreshToken, List<NoteItem> noteItems) {
        disposable.add(mRepository.logoutAccess(accessToken)
                .flatMap((Function<ResponseDataModel, ObservableSource<ResponseDataModel>>) responseDataModel -> mRepository.logoutRefresh(refreshToken))
                .subscribeWith(new DisposableObserver<ResponseDataModel>() {
                    @Override
                    public void onNext(ResponseDataModel responseDataModel) {
                        editor.remove("access_token");
                        editor.remove("refresh_token");
                        editor.remove("username");
                        editor.remove("user_id");
                        editor.remove("is_logged_in");
                        editor.remove("list_fetched");
                        editor.commit();
                        mRepository.deleteAll(noteItems);

                        mView.startLoginActivity();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );
    }


    @Override
    public void onStop() {
        disposable.clear();
    }
}
