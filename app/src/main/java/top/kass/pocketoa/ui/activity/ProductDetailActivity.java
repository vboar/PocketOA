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
                finish();
            }
        });

        loadProduct((ProductBean) getIntent().getSerializableExtra("product"));
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
                                mProductDetailPresenter.deleteProduct(mProductBean);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        }).show();
                break;
            case R.id.action_edit:
                break;
        }
        return false;
    }

    private void loadProduct(ProductBean productBean) {
        mProductBean = productBean;

        TextView tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(mProductBean.getProductName());
        TextView tvSn = (TextView) findViewById(R.id.tvSn);
        tvSn.setText(mProductBean.getProductSn());
        TextView tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvPrice.setText(mProductBean.getStandardPrice().toString());
        TextView tvUnit = (TextView) findViewById(R.id.tvUnit);
        tvUnit.setText(mProductBean.getSalesUnit());
        TextView tvCost = (TextView) findViewById(R.id.tvCost);
        tvCost.setText(mProductBean.getUnitCost().toString());
        TextView tvIntroduction = (TextView) findViewById(R.id.tvIntroduction);
        tvIntroduction.setText(mProductBean.getIntroduction());
        TextView tvRemark = (TextView) findViewById(R.id.tvRemark);
        tvRemark.setText(mProductBean.getProductRemarks());
        CircleImageView ivPicture = (CircleImageView) findViewById(R.id.ivPicture);
        if (productBean.getPicture().equals("")) {
            ivPicture.setImageResource(R.drawable.icon_default);
        } else {
            Glide.with(this).load(productBean.getPicture()).crossFade()
                    .error(R.drawable.icon_default)
                    .into(ivPicture);
        }
    }

    @Override
    public void navigateToMain() {
        Intent intent = new Intent();
        setResult(2, intent);
        finish();
    }

    @Override
    public void showProgress() {
        mProgressDialog = ProgressDialog.show(this, "", "正在删除...", false, false);
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
}
