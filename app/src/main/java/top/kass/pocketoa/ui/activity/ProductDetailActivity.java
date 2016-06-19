package top.kass.pocketoa.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.ProductBean;
import top.kass.pocketoa.presenter.ProductDetailPresenter;
import top.kass.pocketoa.presenter.impl.ProductDetailPresenterImpl;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.view.ProductDetailView;

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailView {

    private Toolbar mToolBar;
    private ProductBean mProductBean;
    private ProgressDialog mProgressDialog;
    private ProductDetailPresenter mProductDetailPresenter;
    private boolean isEdited = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        mProductDetailPresenter = new ProductDetailPresenterImpl(this);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMain(1);
            }
        });

        ProductBean productBean = (ProductBean) getIntent().getSerializableExtra("product");
        mProductDetailPresenter.loadProduct(productBean.getProductId());
    }

    @Override
    public void onBackPressed() {
        navigateToMain(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                new AlertDialog.Builder(this).setTitle("确定要删除该商品吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mProductDetailPresenter.deleteProduct(mProductBean.getProductId());
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        }).show();
                break;
            case R.id.action_edit:
                navigateToEdit();
                break;
        }
        return false;
    }

    @Override
    public void loadProduct(ProductBean productBean) {
        mProductBean = productBean;

        TextView tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(mProductBean.getProductName());
        TextView tvSn = (TextView) findViewById(R.id.tvSn);
        tvSn.setText(mProductBean.getProductSn());
        TextView tvPrice = (TextView) findViewById(R.id.tvPrice);
        if (mProductBean.getStandardPrice() == null) {
            tvPrice.setText("");
        } else {
            tvPrice.setText(mProductBean.getStandardPrice().toString());
        }
        TextView tvUnit = (TextView) findViewById(R.id.tvUnit);
        tvUnit.setText(mProductBean.getSalesUnit());
        TextView tvCost = (TextView) findViewById(R.id.tvCost);
        if (mProductBean.getUnitCost() == null) {
            tvCost.setText("");
        } else {
            tvCost.setText(mProductBean.getUnitCost().toString());
        }
        TextView tvIntroduction = (TextView) findViewById(R.id.tvIntroduction);
        tvIntroduction.setText(mProductBean.getIntroduction());
        TextView tvRemark = (TextView) findViewById(R.id.tvRemark);
        tvRemark.setText(mProductBean.getProductRemarks());
        CircleImageView ivPicture = (CircleImageView) findViewById(R.id.ivPicture);
        if (mProductBean.getPicture().equals("")) {
            ivPicture.setImageResource(R.drawable.icon_default);
        } else {
            Glide.with(this).load(mProductBean.getPicture()).crossFade()
                    .error(R.drawable.icon_default)
                    .into(ivPicture);
        }
    }

    @Override
    public void navigateToEdit() {
        Intent intent = new Intent(this, ProductEditActivity.class);
        intent.putExtra("product", mProductBean);
        startActivityForResult(intent, 1);
    }

    @Override
    public void navigateToMain(int type) {
        Intent intent = new Intent();
        if (type == 1) {
            if (isEdited) {
                setResult(1, intent);
            } else {
                setResult(2, intent);
            }
        } else if (type == 2) {
            setResult(1, intent);
        }
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
        View view = findViewById(R.id.product_detail_layout);
        UIUtil.showSnackBar(view, msg, Snackbar.LENGTH_SHORT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 1) {
            isEdited = true;
            mProductDetailPresenter.loadProduct(mProductBean.getProductId());
        }
    }

}
