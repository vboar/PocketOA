package top.kass.pocketoa.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.ContactBean;
import top.kass.pocketoa.presenter.ContactDetailPresenter;
import top.kass.pocketoa.presenter.impl.ContactDetailPresenterImpl;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.util.UrlUtil;
import top.kass.pocketoa.view.ContactDetailView;

public class ContactDetailActivity extends AppCompatActivity implements ContactDetailView {

    private Toolbar mToolBar;
    private ContactDetailPresenter mContactDetailPresenter;
    private ContactBean mContactBean;
    private String customerName;
    private ProgressDialog mProgressDialog;
    private boolean isEdited = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        mContactDetailPresenter = new ContactDetailPresenterImpl(this);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMain(1);
            }
        });

        ContactBean contactBean = (ContactBean) getIntent().getSerializableExtra("contact");
        customerName = contactBean.getCustomer().getCustomerName();
        mContactDetailPresenter.loadContact(contactBean.getContactsId());
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
                                mContactDetailPresenter.deleteContact(mContactBean.getContactsId());
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
    public void loadContact(ContactBean contactBean) {
        mContactBean = contactBean;
        TextView tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(mContactBean.getContactsName());
        TextView tvCustomer= (TextView) findViewById(R.id.tvCustomer);
        tvCustomer.setText(Html.fromHtml("來自客戶：<u>" + customerName + "</u>"));
        TextView tvAge= (TextView) findViewById(R.id.tvAge);
        if (mContactBean.getContactsAge() == null) {
            tvAge.setText("");
        } else {
            tvAge.setText(mContactBean.getContactsAge().toString());
        }
        TextView tvGender= (TextView) findViewById(R.id.tvGender);
        tvGender.setText(mContactBean.getContactsGender());
        TextView tvMobile= (TextView) findViewById(R.id.tvMobile);
        tvMobile.setText(mContactBean.getContactsMobile());
        TextView tvTel= (TextView) findViewById(R.id.tvTel);
        tvTel.setText(mContactBean.getContactsTelephone());
        TextView tvEmail= (TextView) findViewById(R.id.tvEmail);
        tvEmail.setText(mContactBean.getContactsEmail());
        TextView tvAddress= (TextView) findViewById(R.id.tvAddress);
        tvAddress.setText(mContactBean.getContactsAddress());
        TextView tvZipcode= (TextView) findViewById(R.id.tvZipcode);
        tvZipcode.setText(mContactBean.getContactsZipcode());
        TextView tvQQ= (TextView) findViewById(R.id.tvQQ);
        tvQQ.setText(mContactBean.getContactsQq());
        TextView tvWechat= (TextView) findViewById(R.id.tvWechat);
        tvWechat.setText(mContactBean.getContactsWechat());
        TextView tvWangwang= (TextView) findViewById(R.id.tvWangwang);
        tvWangwang.setText(mContactBean.getContactsWangwang());
        TextView tvRemark= (TextView) findViewById(R.id.tvRemark);
        tvRemark.setText(mContactBean.getContactsRemarks());
        CircleImageView ivPicture = (CircleImageView) findViewById(R.id.ivPicture);
        Glide.with(this).load(UrlUtil.COMMON_PIC_URL).crossFade()
                .error(R.drawable.icon_default)
                .into(ivPicture);
    }

    @Override
    public void navigateToEdit() {
        Intent intent = new Intent(this, ContactEditActivity.class);
        intent.putExtra("contact", mContactBean);
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
        View view = findViewById(R.id.contact_detail_layout);
        UIUtil.showSnackBar(view, msg, Snackbar.LENGTH_SHORT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 1) {
            isEdited = true;
            mContactDetailPresenter.loadContact(mContactBean.getContactsId());
        }
    }
}
