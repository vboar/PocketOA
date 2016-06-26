package top.kass.pocketoa.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.umeng.analytics.MobclickAgent;

import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.StaffBean;
import top.kass.pocketoa.presenter.RegisterPresenter;
import top.kass.pocketoa.presenter.impl.RegisterPresenterImpl;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.view.RegisterView;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    private Toolbar mToolBar;
    private ProgressDialog mProgressDialog;
    private StaffBean mStaffBean = new StaffBean();
    private RegisterPresenter mRegisterPresenter;

    private EditText mEtUsername;
    private EditText mEtName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegisterPresenter = new RegisterPresenterImpl(this);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEtUsername = (EditText) findViewById(R.id.etUsername);
        mEtName = (EditText) findViewById(R.id.etName);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_add_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_submit:
                mStaffBean.setUserId(mEtUsername.getText().toString());
                mStaffBean.setName(mEtName.getText().toString());
                mRegisterPresenter.register(mStaffBean);
                break;
        }
        return false;
    }

    @Override
    public void navigateToLogin() {
        finish();
    }

    @Override
    public void showProgress() {
        mProgressDialog = ProgressDialog.show(this, "", "正在注册...", false, true);
    }

    @Override
    public void hideProgress() {
        mProgressDialog.hide();
        mProgressDialog.dismiss();
    }

    @Override
    public void showFailMsg(String msg) {
        View view = findViewById(R.id.register_layout);
        UIUtil.showSnackBar(view, msg, Snackbar.LENGTH_SHORT);
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

}
