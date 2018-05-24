package com.example.adrianwong.noted.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.adrianwong.noted.data.NotesRepository;
import com.example.adrianwong.noted.data.remote.UserRepository;
import com.example.adrianwong.noted.ui.list.ListPresenter;
import com.example.adrianwong.noted.ui.login.LoginPresenter;
import com.example.adrianwong.noted.viewmodel.AddViewModelFactory;
import com.example.adrianwong.noted.viewmodel.ListViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class AppModule {

    @Provides
    @Singleton
    ListViewModelFactory provideListViewModelFactory(NotesRepository repository, CompositeDisposable disposable) {
        return new ListViewModelFactory(repository, disposable);
    }

    @Provides
    @Singleton
    AddViewModelFactory provideAddViewModelFactory(NotesRepository repository, CompositeDisposable disposable) {
        return new AddViewModelFactory(repository, disposable);
    }

    @Provides
    @Singleton
    ListPresenter provideListPresenter(NotesRepository repository, SharedPreferences preferences, CompositeDisposable disposable){
        return new ListPresenter(repository, preferences.edit(), disposable);
    }

    @Provides
    @Singleton
    LoginPresenter provideLoginPresenter(UserRepository dataSource, SharedPreferences preferences, CompositeDisposable disposable){
        return new LoginPresenter(dataSource, preferences.edit(), disposable);
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return pref;
    }
}
