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
import top.kass.pocketoa.presenter.ContactEditPresenter;
import top.kass.pocketoa.presenter.impl.ContactEditPresenterImpl;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.view.ContactEditView;

public class ContactEditActivity extends AppCompatActivity implements ContactEditView {

    private Toolbar mToolBar;
    private ContactEditPresenter mContactEditPresenter;
    private ContactBean mContactBean;
    private ProgressDialog mProgressDialog;

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

        mContactEditPresenter = new ContactEditPresenterImpl(this);

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

        mContactBean = (ContactBean) getIntent().getSerializableExtra("contact");

        mEtName.setText(mContactBean.getContactsName());
        mEtCustomer.setText(mContactBean.getCustomerId().toString());
        if (mContactBean.getContactsAge() != null) {
            mEtAge.setText(mContactBean.getContactsAge().toString());
        }
        mEtMobile.setText(mContactBean.getContactsMobile());
        mEtTel.setText(mContactBean.getContactsTelephone());
        mEtEmail.setText(mContactBean.getContactsEmail());
        mEtAddress.setText(mContactBean.getContactsAddress());
        mEtZipcode.setText(mContactBean.getContactsZipcode());
        mEtQQ.setText(mContactBean.getContactsQq());
        mEtWechat.setText(mContactBean.getContactsWechat());
        mEtWangwang.setText(mContactBean.getContactsWangwang());
        mEtRemark.setText(mContactBean.getContactsRemarks());
        if (mContactBean.getContactsGender().equals("女")) {
            mRbMale.setChecked(false);
            mRbFemale.setChecked(true);
        } else {
            mRbMale.setChecked(true);
            mRbFemale.setChecked(false);
        }
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
                mContactBean.setContactsName(mEtName.getText().toString());
                if (mEtCustomer.getText().toString().equals("")) {
                    mContactBean.setCustomerId(0);
                } else {
                    mContactBean.setCustomerId(Integer.parseInt(mEtCustomer.getText().toString()));
                }
                if (mEtAge.getText().toString().equals("")) {
                    mContactBean.setContactsAge(0);
                } else {
                    mContactBean.setContactsAge(Integer.parseInt(mEtAge.getText().toString()));
                }
                if (mRbMale.isChecked()) {
                    mContactBean.setContactsGender("男");
                } else {
                    mContactBean.setContactsGender("女");
                }
                mContactBean.setContactsMobile(mEtMobile.getText().toString());
                mContactBean.setContactsTelephone(mEtTel.getText().toString());
                mContactBean.setContactsEmail(mEtEmail.getText().toString());
                mContactBean.setContactsAddress(mEtAddress.getText().toString());
                mContactBean.setContactsZipcode(mEtZipcode.getText().toString());
                mContactBean.setContactsQq(mEtQQ.getText().toString());
                mContactBean.setContactsWechat(mEtWechat.getText().toString());
                mContactBean.setContactsWangwang(mEtWangwang.getText().toString());
                mContactBean.setContactsRemarks(mEtRemark.getText().toString());
                mContactEditPresenter.editContact(mContactBean);
                break;
        }
        return false;
    }

    @Override
    public void navigateToDetail() {
        Intent intent = new Intent();
        setResult(1, intent);
        finish();
    }

    @Override
    public void showProgress() {
        mProgressDialog = ProgressDialog.show(this, "", "正在编辑...", false, true);
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
