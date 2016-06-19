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

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.ProductBean;
import top.kass.pocketoa.presenter.ProductAddPresenter;
import top.kass.pocketoa.presenter.ProductEditPresenter;
import top.kass.pocketoa.presenter.impl.ProductAddPresenterImpl;
import top.kass.pocketoa.presenter.impl.ProductEditPresenterImpl;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.util.UrlUtil;
import top.kass.pocketoa.view.ProductAddView;
import top.kass.pocketoa.view.ProductEditView;

public class ProductEditActivity extends AppCompatActivity implements ProductEditView {

    private Toolbar mToolBar;

    private int staffId;
    private ProductBean mProductBean;

    private EditText mEtName;
    private EditText mEtSn;
    private EditText mEtPrice;
    private EditText mEtUnit;
    private EditText mEtCost;
    private EditText mEtIntroduction;
    private EditText mEtRemark;
    private EditText mEtPicture;

    private ProgressDialog mProgressDialog;
    private ProductEditPresenter mProductEditPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);

        mProductEditPresenter = new ProductEditPresenterImpl(this);

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

        mProductBean = (ProductBean) getIntent().getSerializableExtra("product");

        mEtName = (EditText) findViewById(R.id.etName);
        mEtSn = (EditText) findViewById(R.id.etSn);
        mEtPrice = (EditText) findViewById(R.id.etPrice);
        mEtUnit = (EditText) findViewById(R.id.etUnit);
        mEtCost = (EditText) findViewById(R.id.etCost);
        mEtIntroduction = (EditText) findViewById(R.id.etIntroduction);
        mEtRemark = (EditText) findViewById(R.id.etRemark);
        mEtPicture = (EditText) findViewById(R.id.etPicture);
        mEtName.setText(mProductBean.getProductName());
        mEtSn.setText(mProductBean.getProductSn());
        if (mProductBean.getStandardPrice() != null) {
            mEtPrice.setText(mProductBean.getStandardPrice().toString());
        }
        mEtUnit.setText(mProductBean.getSalesUnit());
        if (mProductBean.getUnitCost() != null) {
            mEtCost.setText(mProductBean.getUnitCost().toString());
        }
        mEtIntroduction.setText(mProductBean.getIntroduction());
        mEtRemark.setText(mProductBean.getProductRemarks());
        if (mProductBean.getPicture().equals("")) {
            mEtPicture.setText(UrlUtil.COMMON_PIC_URL);
        } else {
            mEtPicture.setText(mProductBean.getPicture());
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
                mProductBean.setProductName(mEtName.getText().toString());
                mProductBean.setProductSn(mEtSn.getText().toString());
                if (!mEtPrice.getText().toString().equals("")) {
                    mProductBean.setStandardPrice(Double.valueOf(mEtPrice.getText().toString()));
                } else {
                    mProductBean.setStandardPrice(0.0);
                }
                if (!mEtCost.getText().toString().equals("")) {
                    mProductBean.setUnitCost(Double.valueOf(mEtCost.getText().toString()));
                } else {
                    mProductBean.setUnitCost(0.0);
                }
                mProductBean.setSalesUnit(mEtUnit.getText().toString());
                mProductBean.setIntroduction(mEtIntroduction.getText().toString());
                mProductBean.setProductRemarks(mEtRemark.getText().toString());
                mProductBean.setPicture(mEtPicture.getText().toString());
                mProductEditPresenter.editProduct(mProductBean);
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
        mProgressDialog = ProgressDialog.show(this, "", "正在编辑...", false, false);
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
