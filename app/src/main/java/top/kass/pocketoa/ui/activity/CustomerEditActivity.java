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

import com.jaredrummler.materialspinner.MaterialSpinner;

import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.CustomerBean;
import top.kass.pocketoa.presenter.CustomerEditPresenter;
import top.kass.pocketoa.presenter.impl.CustomerEditPresenterImpl;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.view.CustomerEditView;

public class CustomerEditActivity extends AppCompatActivity implements CustomerEditView {

    private Toolbar mToolBar;
    private ProgressDialog mProgressDialog;
    private CustomerEditPresenter mCustomerEditPresenter;
    private CustomerBean mCustomerBean;

    private EditText mEtName;
    private EditText mEtProfile;
    private EditText mEtSource;
    private EditText mEtSize;
    private EditText mEtTel;
    private EditText mEtEmail;
    private EditText mEtWebsite;
    private EditText mEtAddress;
    private EditText mEtZipcode;
    private EditText mEtRemark;
    private MaterialSpinner mSpType;
    private MaterialSpinner mSpStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add);

        mCustomerEditPresenter = new CustomerEditPresenterImpl(this);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mCustomerBean = (CustomerBean) getIntent().getSerializableExtra("customer");

        mEtName = (EditText) findViewById(R.id.etName);
        mEtProfile = (EditText) findViewById(R.id.etProfile);
        mEtName = (EditText) findViewById(R.id.etName);
        mEtSource = (EditText) findViewById(R.id.etSource);
        mEtSize = (EditText) findViewById(R.id.etSize);
        mEtTel = (EditText) findViewById(R.id.etTel);
        mEtEmail = (EditText) findViewById(R.id.etEmail);
        mEtWebsite = (EditText) findViewById(R.id.etWebsite);
        mEtAddress = (EditText) findViewById(R.id.etAddress);
        mEtZipcode = (EditText) findViewById(R.id.etZipcode);
        mEtRemark = (EditText) findViewById(R.id.etRemark);

        mEtName.setText(mCustomerBean.getCustomerName());
        mEtProfile.setText(mCustomerBean.getProfile());
        mEtSource.setText(mCustomerBean.getCustomerSource());
        if (mCustomerBean.getSize() != null) {
            mEtSize.setText(mCustomerBean.getSize().toString());
        }
        mEtTel.setText(mCustomerBean.getTelephone());
        mEtEmail.setText(mCustomerBean.getEmail());
        mEtWebsite.setText(mCustomerBean.getWebsite());
        mEtAddress.setText(mCustomerBean.getAddress());
        mEtZipcode.setText(mCustomerBean.getZipcode());
        mEtRemark.setText(mCustomerBean.getCustomerRemarks());

        mSpType = (MaterialSpinner) findViewById(R.id.spType);
        mSpType.setItems(
                getString(R.string.customer_type_important),
                getString(R.string.customer_type_normal),
                getString(R.string.customer_type_low_value)
        );
        mSpType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mCustomerBean.setCustomerType(position+1);
            }
        });
        if (mCustomerBean.getCustomerType() != null) {
            mSpType.setSelectedIndex(mCustomerBean.getCustomerType()-1);
        } else {
            mCustomerBean.setCustomerType(1);
        }

        mSpStatus = (MaterialSpinner) findViewById(R.id.spStatus);
        mSpStatus.setItems(
                getString(R.string.customer_status_1),
                getString(R.string.customer_status_2),
                getString(R.string.customer_status_3),
                getString(R.string.customer_status_4),
                getString(R.string.customer_status_5)
        );
        mSpStatus.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mCustomerBean.setCustomerStatus(position+1);
            }
        });
        if (mCustomerBean.getCustomerStatus() != null) {
            mSpStatus.setSelectedIndex(mCustomerBean.getCustomerStatus()-1);
        } else {
            mCustomerBean.setCustomerStatus(1);
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
                mCustomerBean.setCustomerName(mEtName.getText().toString());
                mCustomerBean.setProfile(mEtProfile.getText().toString());
                mCustomerBean.setCustomerSource(mEtSource.getText().toString());
                if (mEtSize.getText().toString().equals("")) {
                    mCustomerBean.setSize(0);
                } else {
                    mCustomerBean.setSize(Integer.parseInt(mEtSize.getText().toString()));
                }
                mCustomerBean.setTelephone(mEtTel.getText().toString());
                mCustomerBean.setEmail(mEtEmail.getText().toString());
                mCustomerBean.setWebsite(mEtWebsite.getText().toString());
                mCustomerBean.setAddress(mEtAddress.getText().toString());
                mCustomerBean.setZipcode(mEtZipcode.getText().toString());
                mCustomerBean.setCustomerRemarks(mEtRemark.getText().toString());
                mCustomerEditPresenter.editCustomer(mCustomerBean);
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
        View view = findViewById(R.id.customer_add_layout);
        UIUtil.showSnackBar(view, msg, Snackbar.LENGTH_SHORT);
    }

}
