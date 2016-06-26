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

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.List;

import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.ContactBean;
import top.kass.pocketoa.bean.CustomerBean;
import top.kass.pocketoa.presenter.ContactAddPresenter;
import top.kass.pocketoa.presenter.impl.ContactAddPresenterImpl;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.view.ContactAddView;

public class ContactAddActivity extends AppCompatActivity implements ContactAddView {

    private Toolbar mToolBar;

    private ProgressDialog mProgressDialog;

    private ContactAddPresenter mContactAddPresenter;

    private EditText mEtName;
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
    private MaterialSpinner mSpCustomer;

    private ContactBean mContactBean = new ContactBean();
    private int defaultCustomerId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);

        mContactAddPresenter = new ContactAddPresenterImpl(this);

        defaultCustomerId = getIntent().getIntExtra("customerId", 0);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEtName = (EditText) findViewById(R.id.etName);
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

        mSpCustomer = (MaterialSpinner) findViewById(R.id.spCustomer);
        mContactAddPresenter.loadCustomers();
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
                mContactAddPresenter.addContact(mContactBean);
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
    public void loadCustomers(List<CustomerBean> list) {
        final int[] cids = new int[list.size()];
        String[] cnames = new String[list.size()];
        int position = 0;
        for (int i = 0; i < list.size(); i++) {
            cids[i] = list.get(i).getCustomerId();
            cnames[i] = list.get(i).getCustomerName();

            if (defaultCustomerId == cids[i]) {
                position = i;
            }
        }
        mSpCustomer.setItems(cnames);
        mSpCustomer.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                mContactBean.setCustomerId(cids[position]);
            }
        });
        mSpCustomer.setSelectedIndex(position);
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
