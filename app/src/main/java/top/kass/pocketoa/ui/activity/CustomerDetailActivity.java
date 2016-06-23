package top.kass.pocketoa.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.CustomerBean;
import top.kass.pocketoa.presenter.CustomerDetailPresenter;
import top.kass.pocketoa.presenter.impl.CustomerDetailPresenterImpl;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.util.UrlUtil;
import top.kass.pocketoa.view.CustomerDetailView;

public class CustomerDetailActivity extends AppCompatActivity implements CustomerDetailView {

    private Toolbar mToolBar;
    private boolean canEdited = false;
    private boolean isEdited = false;
    private CustomerBean mCustomerBean;
    private CustomerDetailPresenter mCustomerDetailPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);

        mCustomerDetailPresenter = new CustomerDetailPresenterImpl(this);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMain(1);
            }
        });

        CustomerBean customerBean = (CustomerBean) getIntent().getSerializableExtra("customer");

        int staffId = getSharedPreferences("oa", MODE_PRIVATE).getInt("staffId", 0);
        if (staffId == customerBean.getStaffId()) {
            canEdited = true;
        } else {
            View btnGroup = (View) findViewById(R.id.btnGroup);
            btnGroup.setVisibility(View.GONE);
        }
        mCustomerDetailPresenter.loadCustomer(customerBean.getCustomerId());

    }

    @Override
    public void onBackPressed() {
        navigateToMain(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (canEdited) {
            getMenuInflater().inflate(R.menu.common_detail, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                new AlertDialog.Builder(this).setTitle("确定要删除该客户吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mCustomerDetailPresenter.deleteCustomer(mCustomerBean.getCustomerId());
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
    public void loadCustomer(CustomerBean customerBean) {
        mCustomerBean = customerBean;
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvType = (TextView) findViewById(R.id.tvType);
        TextView tvStatus = (TextView) findViewById(R.id.tvStatus);
        TextView tvProfile = (TextView) findViewById(R.id.tvProfile);
        TextView tvSource = (TextView) findViewById(R.id.tvSource);
        TextView tvSize = (TextView) findViewById(R.id.tvSize);
        TextView tvTel = (TextView) findViewById(R.id.tvTel);
        TextView tvEmail = (TextView) findViewById(R.id.tvEmail);
        TextView tvWebsite = (TextView) findViewById(R.id.tvWebsite);
        TextView tvAddress = (TextView) findViewById(R.id.tvAddress);
        TextView tvZipcode = (TextView) findViewById(R.id.tvZipcode);
        TextView tvRemark = (TextView) findViewById(R.id.tvRemark);
        tvName.setText(mCustomerBean.getCustomerName());
        tvType.setText(CustomerBean.getTypeString(mCustomerBean.getCustomerType()));
        tvStatus.setText(CustomerBean.getStatusString(mCustomerBean.getCustomerStatus()));
        tvProfile.setText(mCustomerBean.getProfile());
        tvSource.setText(mCustomerBean.getCustomerSource());
        if (mCustomerBean.getSize() == null) {
            tvSize.setText("");
        } else {
            tvSize.setText(mCustomerBean.getSize().toString());
        }
        tvTel.setText(mCustomerBean.getTelephone());
        tvEmail.setText(mCustomerBean.getEmail());
        tvWebsite.setText(mCustomerBean.getWebsite());
        tvAddress.setText(mCustomerBean.getAddress());
        tvZipcode.setText(mCustomerBean.getZipcode());
        tvRemark.setText(mCustomerBean.getCustomerRemarks());

        CircleImageView ivPicture = (CircleImageView) findViewById(R.id.ivPicture);
        Glide.with(this).load(UrlUtil.COMMON_PIC_URL).crossFade()
                .error(R.drawable.icon_default)
                .into(ivPicture);
    }

    @Override
    public void navigateToEdit() {
        Intent intent = new Intent(this, CustomerEditActivity.class);
        intent.putExtra("customer", mCustomerBean);
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
        mProgressDialog = ProgressDialog.show(this, "", msg, false, true);
    }

    @Override
    public void hideProgress() {
        mProgressDialog.hide();
        mProgressDialog.dismiss();
    }

    @Override
    public void showFailMsg(String msg) {
        View view = findViewById(R.id.customer_detail_layout);
        UIUtil.showSnackBar(view, msg, Snackbar.LENGTH_SHORT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 1) {
            isEdited = true;
            mCustomerDetailPresenter.loadCustomer(mCustomerBean.getCustomerId());
        }
    }

}
