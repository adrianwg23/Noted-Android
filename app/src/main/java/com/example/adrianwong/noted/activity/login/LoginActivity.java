package com.example.adrianwong.noted.activity.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adrianwong.noted.R;
import com.example.adrianwong.noted.activity.list.ListActivity;
import com.example.adrianwong.noted.util.NetworkHelper;
import com.example.adrianwong.noted.util.PresenterHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginView {

    @BindView(R.id.et_username)
    EditText mUsernameEditText;

    @BindView(R.id.et_password)
    EditText mPasswordEditText;

    @BindView(R.id.button_login)
    Button mLoginButton;

    @BindView(R.id.tv_sign_up)
    TextView mSignUpTextView;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mLoginButton.setOnClickListener(this);
        mSignUpTextView.setOnClickListener(this);

        presenter = new LoginPresenter(this, PresenterHelper.getDataSource(), PresenterHelper.getDisposable());
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.button_login || viewId == R.id.tv_sign_up) {
            String username = mUsernameEditText.getText().toString();
            String password = mPasswordEditText.getText().toString();

            if (viewId == R.id.button_login) {
                presenter.loginOrRegister(username, password, NetworkHelper.LOGIN);
            } else {
                presenter.loginOrRegister(username, password, NetworkHelper.REGISTER);
            }
        }
    }

    @Override
    public void startListActivity() {
        Intent i = new Intent(this, ListActivity.class);
        startActivity(i);
    }

    @Override
    public void toastMessage(String token) {
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
    }
}
