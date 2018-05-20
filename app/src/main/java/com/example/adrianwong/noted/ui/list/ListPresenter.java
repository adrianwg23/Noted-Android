package com.example.adrianwong.noted.ui.list;

import com.example.adrianwong.noted.data.remote.RepositoryDataSourceInterface;
import com.example.adrianwong.noted.ui.base.BasePresenter;
import com.example.adrianwong.noted.util.PresenterHelper;

import io.reactivex.disposables.CompositeDisposable;

public class ListPresenter extends BasePresenter<ListContract.ListView> implements ListContract.ViewActions {

    private RepositoryDataSourceInterface dataSource;
    private CompositeDisposable disposable;

    public ListPresenter(RepositoryDataSourceInterface dataSource) {
        this.dataSource = dataSource;
        this.disposable = PresenterHelper.getDisposable();
    }


    @Override
    public void onAddFabClick() {
        mView.startAddActivity();
    }

    @Override
    public void onStop() {
        disposable.clear();
    }
}
