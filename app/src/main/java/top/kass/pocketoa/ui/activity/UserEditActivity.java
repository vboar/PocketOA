package top.kass.pocketoa.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.StaffBean;
import top.kass.pocketoa.presenter.UserEditPresenter;
import top.kass.pocketoa.presenter.impl.UserEditPresenterImpl;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.view.UserEditView;

public class UserEditActivity extends AppCompatActivity implements UserEditView {

    private Toolbar mToolBar;
    private ProgressDialog mProgressDialog;
    private UserEditPresenter mUserEditPresenter;
    private StaffBean mStaffBean;

    private EditText mEtUsername;
    private EditText mEtName;
    private EditText mEtMobile;
    private EditText mEtTel;
    private EditText mEtEmail;
    private RadioGroup mRgGender;
    private RadioButton mRbMale;
    private RadioButton mRbFemale;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        mUserEditPresenter = new UserEditPresenterImpl(this);
        mStaffBean = (StaffBean) getIntent().getSerializableExtra("staffBean");
        mUserEditPresenter.loadStaff(mStaffBean.getStaffId());

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
        mEtMobile = (EditText) findViewById(R.id.etMobile);
        mEtTel = (EditText) findViewById(R.id.etTel);
        mEtEmail = (EditText) findViewById(R.id.etEmail);
        mRgGender = (RadioGroup) findViewById(R.id.rgGender);
        mRbMale = (RadioButton) findViewById(R.id.rbMale);
        mRbFemale = (RadioButton) findViewById(R.id.rbFemale);

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
                mStaffBean.setMobile(mEtMobile.getText().toString());
                mStaffBean.setTel(mEtTel.getText().toString());
                mStaffBean.setEmail(mEtEmail.getText().toString());
                if (mRbMale.isChecked()) {
                    mStaffBean.setGender("男");
                } else {
                    mStaffBean.setGender("女");
                }
                mUserEditPresenter.editStaff(mStaffBean);
                break;
        }
        return false;
    }

    @Override
    public void setStaffBean(StaffBean staffBean) {
        mStaffBean = staffBean;
        mEtUsername.setText(mStaffBean.getUserId());
        mEtName.setText(mStaffBean.getName());
        mEtMobile.setText(mStaffBean.getMobile());
        mEtTel.setText(mStaffBean.getTel());
        mEtEmail.setText(mStaffBean.getEmail());
        if (staffBean.getGender().equals("女")) {
            mRbFemale.setChecked(true);
        } else {
            mRbMale.setChecked(true);
        }
    }

    @Override
    public void navigateToMain() {
        Intent intent = new Intent();
        intent.putExtra("staffBean", mStaffBean);
        setResult(99, intent);
        finish();
    }

    @Override
    public void showProgress(String msg) {
        mProgressDialog = ProgressDialog.show(this, "", msg, false, false);
    }

    @Override
    public void hideProgress() {
        mProgressDialog.hide();
        mProgressDialog.dismiss();
    }

    @Override
    public void showFailMsg(String msg) {
        View view = findViewById(R.id.user_edit_layout);
        UIUtil.showSnackBar(view, msg, Snackbar.LENGTH_SHORT);
    }

}
