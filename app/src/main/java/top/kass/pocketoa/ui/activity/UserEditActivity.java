package top.kass.pocketoa.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;

import de.hdodenhof.circleimageview.CircleImageView;
import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.StaffBean;
import top.kass.pocketoa.presenter.UserEditPresenter;
import top.kass.pocketoa.presenter.impl.UserEditPresenterImpl;
import top.kass.pocketoa.util.ImageUriUtil;
import top.kass.pocketoa.util.ToolsUtil;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.util.UrlUtil;
import top.kass.pocketoa.view.UserEditView;

public class UserEditActivity extends AppCompatActivity implements UserEditView {

    private Toolbar mToolBar;
    private ProgressDialog mProgressDialog;
    private UserEditPresenter mUserEditPresenter;
    private StaffBean mStaffBean;

    private EditText mEtUsername;
    private EditText mEtName;
    private EditText mEtMobile;
    private EditText mEtTel;
    private EditText mEtEmail;
    private RadioButton mRbMale;
    private RadioButton mRbFemale;
    private CircleImageView mIvPicture;
    private Button mBtnSelectPic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        mUserEditPresenter = new UserEditPresenterImpl(this);
        mStaffBean = (StaffBean) getIntent().getSerializableExtra("staffBean");
        mUserEditPresenter.loadStaff(mStaffBean.getStaffId());

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEtUsername = (EditText) findViewById(R.id.etUsername);
        mEtName = (EditText) findViewById(R.id.etName);
        mEtMobile = (EditText) findViewById(R.id.etMobile);
        mEtTel = (EditText) findViewById(R.id.etTel);
        mEtEmail = (EditText) findViewById(R.id.etEmail);
        mRbMale = (RadioButton) findViewById(R.id.rbMale);
        mRbFemale = (RadioButton) findViewById(R.id.rbFemale);

        mIvPicture = (CircleImageView) findViewById(R.id.ivPicture);
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
                mStaffBean.setUserId(mEtUsername.getText().toString());
                mStaffBean.setName(mEtName.getText().toString());
                mStaffBean.setMobile(mEtMobile.getText().toString());
                mStaffBean.setTel(mEtTel.getText().toString());
                mStaffBean.setEmail(mEtEmail.getText().toString());
                if (mRbMale.isChecked()) {
                    mStaffBean.setGender("男");
                } else {
                    mStaffBean.setGender("女");
                }
                mUserEditPresenter.editStaff(mStaffBean);
                break;
        }
        return false;
    }

    @Override
    public void setStaffBean(StaffBean staffBean) {
        mStaffBean = staffBean;
        mEtUsername.setText(mStaffBean.getUserId());
        mEtName.setText(mStaffBean.getName());
        mEtMobile.setText(mStaffBean.getMobile());
        mEtTel.setText(mStaffBean.getTel());
        mEtEmail.setText(mStaffBean.getEmail());
        if (staffBean.getGender().equals("女")) {
            mRbFemale.setChecked(true);
        } else {
            mRbMale.setChecked(true);
        }
        if (!mStaffBean.getAvatar().equals("")) {
            Glide.with(this).load(staffBean.getAvatar()).crossFade()
                    .error(R.drawable.icon_default)
                    .into(mIvPicture);
        } else {
            mIvPicture.setImageResource(R.drawable.icon_default);
        }
    }

    @Override
    public void navigateToMain() {
        Intent intent = new Intent();
        intent.putExtra("staffBean", mStaffBean);
        setResult(99, intent);
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
        View view = findViewById(R.id.user_edit_layout);
        UIUtil.showSnackBar(view, msg, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void showImage(String url) {
        mStaffBean.setAvatar(url);
        Glide.with(this).load(mStaffBean.getAvatar()).crossFade()
                .error(R.drawable.icon_default)
                .into(mIvPicture);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            mUserEditPresenter.uploadImage(ImageUriUtil.getImageAbsolutePath(this, uri));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
