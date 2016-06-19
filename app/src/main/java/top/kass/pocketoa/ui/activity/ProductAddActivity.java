package top.kass.pocketoa.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.ProductBean;
import top.kass.pocketoa.presenter.ProductAddPresenter;
import top.kass.pocketoa.presenter.impl.ProductAddPresenterImpl;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.util.UrlUtil;
import top.kass.pocketoa.view.ProductAddView;

public class ProductAddActivity extends AppCompatActivity implements ProductAddView {

    private Toolbar mToolBar;

    private int staffId;

    private EditText mEtName;
    private EditText mEtSn;
    private EditText mEtPrice;
    private EditText mEtUnit;
    private EditText mEtCost;
    private EditText mEtIntroduction;
    private EditText mEtRemark;
    private EditText mEtPicture;

    private ProgressDialog mProgressDialog;
    private ProductAddPresenter mProductAddPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);

        mProductAddPresenter = new ProductAddPresenterImpl(this);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("oa", MODE_PRIVATE);
        staffId = sharedPreferences.getInt("staffId", 0);

        mEtName = (EditText) findViewById(R.id.etName);
        mEtSn = (EditText) findViewById(R.id.etSn);
        mEtPrice = (EditText) findViewById(R.id.etPrice);
        mEtUnit = (EditText) findViewById(R.id.etUnit);
        mEtCost = (EditText) findViewById(R.id.etCost);
        mEtIntroduction = (EditText) findViewById(R.id.etIntroduction);
        mEtRemark = (EditText) findViewById(R.id.etRemark);
        mEtPicture = (EditText) findViewById(R.id.etPicture);
        mEtPicture.setText(UrlUtil.COMMON_PIC_URL);
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
                ProductBean productBean = new ProductBean();
                productBean.setProductName(mEtName.getText().toString());
                productBean.setProductSn(mEtSn.getText().toString());
                if (!mEtPrice.getText().toString().equals("")) {
                    productBean.setStandardPrice(Double.valueOf(mEtPrice.getText().toString()));
                } else {
                    productBean.setStandardPrice(0.0);
                }
                if (!mEtCost.getText().toString().equals("")) {
                    productBean.setUnitCost(Double.valueOf(mEtCost.getText().toString()));
                } else {
                    productBean.setUnitCost(0.0);
                }
                productBean.setSalesUnit(mEtUnit.getText().toString());
                productBean.setIntroduction(mEtIntroduction.getText().toString());
                productBean.setProductRemarks(mEtRemark.getText().toString());
                productBean.setPicture(mEtPicture.getText().toString());
                mProductAddPresenter.addProduct(productBean);
                break;
        }
        return false;
    }

    @Override
    public void navigateToMain() {
        Intent intent = new Intent();
        setResult(5, intent);
        finish();
    }

    @Override
    public void showProgress() {
        mProgressDialog = ProgressDialog.show(this, "", "正在添加...", false, false);
    }

    @Override
    public void hideProgress() {
        mProgressDialog.hide();
        mProgressDialog.dismiss();
    }

    @Override
    public void showFailMsg(String msg) {
        View view = findViewById(R.id.product_add_layout);
        UIUtil.showSnackBar(view, msg, Snackbar.LENGTH_SHORT);
    }
}
