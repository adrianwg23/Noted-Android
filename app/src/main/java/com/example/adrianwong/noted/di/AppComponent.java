package com.example.adrianwong.noted.di;

import com.example.adrianwong.noted.ui.add.AddFragment;
import com.example.adrianwong.noted.ui.list.ListFragment;
import com.example.adrianwong.noted.ui.login.LoginActivity;
import com.example.adrianwong.noted.ui.login.LoginFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AppModule.class, DataModule.class})
public interface AppComponent {

    void inject(LoginFragment loginFragment);
    void inject(ListFragment listFragment);
    void inject(AddFragment addFragment);
    void inject(LoginActivity loginActivity);
}
