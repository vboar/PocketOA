package top.kass.pocketoa.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.ProductBean;
import top.kass.pocketoa.presenter.ProductAddPresenter;
import top.kass.pocketoa.presenter.ProductEditPresenter;
import top.kass.pocketoa.presenter.impl.ProductAddPresenterImpl;
import top.kass.pocketoa.presenter.impl.ProductEditPresenterImpl;
import top.kass.pocketoa.util.ImageUriUtil;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.util.UrlUtil;
import top.kass.pocketoa.view.ProductAddView;
import top.kass.pocketoa.view.ProductEditView;

public class ProductEditActivity extends AppCompatActivity implements ProductEditView {

    private Toolbar mToolBar;

    private ProductBean mProductBean;

    private EditText mEtName;
    private EditText mEtSn;
    private EditText mEtPrice;
    private EditText mEtUnit;
    private EditText mEtCost;
    private EditText mEtIntroduction;
    private EditText mEtRemark;
    private CircleImageView mIvPicture;
    private Button mBtnSelectPic;

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

        mProductBean = (ProductBean) getIntent().getSerializableExtra("product");

        mEtName = (EditText) findViewById(R.id.etName);
        mEtSn = (EditText) findViewById(R.id.etSn);
        mEtPrice = (EditText) findViewById(R.id.etPrice);
        mEtUnit = (EditText) findViewById(R.id.etUnit);
        mEtCost = (EditText) findViewById(R.id.etCost);
        mEtIntroduction = (EditText) findViewById(R.id.etIntroduction);
        mEtRemark = (EditText) findViewById(R.id.etRemark);
        mIvPicture = (CircleImageView) findViewById(R.id.ivPicture);

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
            mIvPicture.setImageResource(R.drawable.icon_default);
        } else {
            Glide.with(this).load(mProductBean.getPicture()).crossFade()
                    .error(R.drawable.icon_default)
                    .into(mIvPicture);
        }

        mBtnSelectPic = (Button) findViewById(R.id.btnSelectPic);
        mBtnSelectPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
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
        mProgressDialog = ProgressDialog.show(this, "", "正在编辑...", false, true);
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

    @Override
    public void showImage(String url) {
        mProductBean.setPicture(UrlUtil.COMMON_PIC_URL);
        Glide.with(this).load(url).crossFade()
                .error(R.drawable.icon_default)
                .into(mIvPicture);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            mProductEditPresenter.uploadImage(ImageUriUtil.getImageAbsolutePath(this, uri));
        }
    }

}
