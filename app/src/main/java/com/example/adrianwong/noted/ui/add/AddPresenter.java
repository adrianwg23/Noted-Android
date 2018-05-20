package com.example.adrianwong.noted.ui.add;

import com.example.adrianwong.noted.data.remote.RepositoryDataSourceInterface;
import com.example.adrianwong.noted.ui.base.BasePresenter;
import com.example.adrianwong.noted.util.PresenterHelper;

import io.reactivex.disposables.CompositeDisposable;

public class AddPresenter extends BasePresenter<AddContract.AddView> implements AddContract.ViewActions {

    private RepositoryDataSourceInterface dataSource;
    private CompositeDisposable disposable;

    public AddPresenter(RepositoryDataSourceInterface dataSource) {
        this.dataSource = dataSource;
        this.disposable = PresenterHelper.getDisposable();
    }


    @Override
    public void onStop() {
        disposable.clear();
    }

    @Override
    public void onNoteSaved(String noteTitle, String noteBody, int priorityColourId) {

    }
}
