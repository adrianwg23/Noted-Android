package com.example.adrianwong.noted.ui.login;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adrianwong.noted.R;
import com.example.adrianwong.noted.ui.list.ListActivity;
import com.example.adrianwong.noted.util.PresenterHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements LoginContract.LoginView, View.OnClickListener {

    @BindView(R.id.et_username) EditText mUsernameEditText;
    @BindView(R.id.et_password) EditText mPasswordEditText;
    @BindView(R.id.button_login) Button mLoginButton;
    @BindView(R.id.tv_sign_up) TextView mSignUpTextView;
    @BindView(R.id.progress_bar_login) ProgressBar mLoginProgressBar;

    private LoginPresenter mLoginPresenter;


    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);

        mLoginButton.setOnClickListener(this);
        mSignUpTextView.setOnClickListener(this);
        mLoginProgressBar.setVisibility(View.INVISIBLE);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        Editor editor = pref.edit();

        mLoginPresenter = new LoginPresenter(PresenterHelper.getDataSource(), editor);
        mLoginPresenter.attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mLoginPresenter.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();
    }

    @Override
    public void showToast(String message) {
        mLoginProgressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        mLoginProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void startListActivity() {
        mLoginProgressBar.setVisibility(View.GONE);
        Intent intent = new Intent(getActivity(), ListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.button_login || viewId == R.id.tv_sign_up) {
            String username = mUsernameEditText.getText().toString();
            String password = mPasswordEditText.getText().toString();

            if ( TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                showToast("Please enter a valid username and/or password");
                return;
            }

            if (viewId == R.id.button_login) {
                mLoginPresenter.onLoginClicked(username, password);
            } else {
                mLoginPresenter.onRegisterClicked(username, password);
            }
        }
    }
}
