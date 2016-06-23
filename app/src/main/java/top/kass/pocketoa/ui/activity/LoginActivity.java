package top.kass.pocketoa.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.StaffBean;
import top.kass.pocketoa.presenter.LoginPresenter;
import top.kass.pocketoa.presenter.impl.LoginPresenterImpl;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.view.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private long firstTime = 0;
    private ProgressDialog mProgressDialog;
    private LoginPresenter mLoginPresenter;
    private EditText mEtUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoginPresenter = new LoginPresenterImpl(this);

        setContentView(R.layout.activity_login);

        mEtUsername = (EditText) findViewById(R.id.etUsername);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        assert btnLogin != null;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.login(mEtUsername.getText().toString());
            }
        });
        TextView tvRegister = (TextView) findViewById(R.id.tvRegister);
        assert tvRegister != null;
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToRegister();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            firstTime = secondTime;
            View view = findViewById(R.id.login_layout);
            UIUtil.showSnackBar(view, getString(R.string.back_pressed_again), Snackbar.LENGTH_SHORT);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void navigateToMain(StaffBean staffBean) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("staffBean", staffBean);
        startActivity(intent);
        finish();
    }

    @Override
    public void navigateToRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void showProgress() {
        mProgressDialog = ProgressDialog.show(this, getString(R.string.login),
                getString(R.string.login_loading), false, true);
    }

    @Override
    public void hideProgress() {
        mProgressDialog.hide();
        mProgressDialog.dismiss();
    }

    @Override
    public void showFailMsg(String msg) {
        View view = findViewById(R.id.login_layout);
        UIUtil.showSnackBar(view, msg, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void hideInput() {
        InputMethodManager inputMethodManager =
                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mEtUsername.getWindowToken(), 0);
    }

}
