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

import com.umeng.analytics.MobclickAgent;

import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.FollowUpBean;
import top.kass.pocketoa.presenter.FollowUpAddPresenter;
import top.kass.pocketoa.presenter.impl.FollowUpAddPresenterImpl;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.view.FollowUpAddView;

public class FollowUpAddActivity extends AppCompatActivity implements FollowUpAddView {

    private Toolbar mToolBar;

    private EditText mEtContent;
    private EditText mEtRemark;

    private ProgressDialog mProgressDialog;
    private FollowUpAddPresenter mFollowUpAddPresenter;

    private FollowUpBean mFollowUpBean = new FollowUpBean();
    private int sourceId;
    private int sourceType;
    private int staffId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followup_add);

        mFollowUpAddPresenter = new FollowUpAddPresenterImpl(this);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEtContent = (EditText) findViewById(R.id.etContent);
        mEtRemark = (EditText) findViewById(R.id.etRemark);

        sourceId = getIntent().getIntExtra("sourceId", 0);
        sourceType = getIntent().getIntExtra("sourceType", 0);

        SharedPreferences sharedPreferences = getSharedPreferences("oa", MODE_PRIVATE);
        int staffId =  sharedPreferences.getInt("staffId", 0);

        mFollowUpBean.setSourceId(sourceId);
        mFollowUpBean.setSourceType(sourceType);
        mFollowUpBean.setCreatorId(staffId);
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
                mFollowUpBean.setContent(mEtContent.getText().toString());
                mFollowUpBean.setFollowUpRemarks(mEtRemark.getText().toString());
                mFollowUpAddPresenter.addFollowUp(mFollowUpBean);
                break;
        }
        return false;
    }

    @Override
    public void navigateToList() {
        Intent intent = new Intent();
        setResult(1, intent);
        finish();
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
        View view = findViewById(R.id.product_add_layout);
        UIUtil.showSnackBar(view, msg, Snackbar.LENGTH_SHORT);
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
