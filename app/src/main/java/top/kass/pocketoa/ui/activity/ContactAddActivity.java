package top.kass.pocketoa.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.ContactBean;
import top.kass.pocketoa.presenter.ContactAddPresenter;
import top.kass.pocketoa.presenter.impl.ContactAddPresenterImpl;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.view.ContactAddView;

public class ContactAddActivity extends AppCompatActivity implements ContactAddView {

    private Toolbar mToolBar;

    private ProgressDialog mProgressDialog;

    private ContactAddPresenter mContactAddPresenter;

    private EditText mEtName;
    private EditText mEtCustomer;
    private EditText mEtAge;
    private EditText mEtMobile;
    private EditText mEtTel;
    private EditText mEtEmail;
    private EditText mEtAddress;
    private EditText mEtZipcode;
    private EditText mEtQQ;
    private EditText mEtWechat;
    private EditText mEtWangwang;
    private EditText mEtRemark;
    private RadioButton mRbMale;
    private RadioButton mRbFemale;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);

        mContactAddPresenter = new ContactAddPresenterImpl(this);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEtName = (EditText) findViewById(R.id.etName);
        mEtCustomer = (EditText) findViewById(R.id.etCustomer);
        mEtAge = (EditText) findViewById(R.id.etAge);
        mEtMobile = (EditText) findViewById(R.id.etMobile);
        mEtTel = (EditText) findViewById(R.id.etTel);
        mEtEmail = (EditText) findViewById(R.id.etEmail);
        mEtAddress = (EditText) findViewById(R.id.etAddress);
        mEtZipcode = (EditText) findViewById(R.id.etZipcode);
        mEtQQ = (EditText) findViewById(R.id.etQQ);
        mEtWechat = (EditText) findViewById(R.id.etWechat);
        mEtWangwang = (EditText) findViewById(R.id.etWangwang);
        mEtRemark = (EditText) findViewById(R.id.etRemark);
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
                ContactBean contactBean = new ContactBean();
                contactBean.setContactsName(mEtName.getText().toString());
                if (mEtCustomer.getText().toString().equals("")) {
                    contactBean.setCustomerId(0);
                } else {
                    contactBean.setCustomerId(Integer.parseInt(mEtCustomer.getText().toString()));
                }
                if (mEtAge.getText().toString().equals("")) {
                    contactBean.setContactsAge(0);
                } else {
                    contactBean.setContactsAge(Integer.parseInt(mEtAge.getText().toString()));
                }
                if (mRbMale.isChecked()) {
                    contactBean.setContactsGender("男");
                } else {
                    contactBean.setContactsGender("女");
                }
                contactBean.setContactsMobile(mEtMobile.getText().toString());
                contactBean.setContactsTelephone(mEtTel.getText().toString());
                contactBean.setContactsEmail(mEtEmail.getText().toString());
                contactBean.setContactsAddress(mEtAddress.getText().toString());
                contactBean.setContactsZipcode(mEtZipcode.getText().toString());
                contactBean.setContactsQq(mEtQQ.getText().toString());
                contactBean.setContactsWechat(mEtWechat.getText().toString());
                contactBean.setContactsWangwang(mEtWangwang.getText().toString());
                contactBean.setContactsRemarks(mEtRemark.getText().toString());
                mContactAddPresenter.addContact(contactBean);
                break;
        }
        return false;
    }

    @Override
    public void navigateToMain() {
        Intent intent = new Intent();
        setResult(2, intent);
        finish();
    }

    @Override
    public void showProgress() {
        mProgressDialog = ProgressDialog.show(this, "", "正在添加...", false, true);
    }

    @Override
    public void hideProgress() {
        mProgressDialog.hide();
        mProgressDialog.dismiss();
    }

    @Override
    public void showFailMsg(String msg) {
        View view = findViewById(R.id.contact_add_layout);
        UIUtil.showSnackBar(view, msg, Snackbar.LENGTH_SHORT);
    }

}
