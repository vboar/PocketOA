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

import com.umeng.analytics.MobclickAgent;

import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.FollowUpBean;
import top.kass.pocketoa.presenter.FollowUpEditPresenter;
import top.kass.pocketoa.presenter.impl.FollowUpEditPresenterImpl;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.view.FollowUpEditView;

public class FollowUpEditActivity extends AppCompatActivity implements FollowUpEditView {

    private Toolbar mToolBar;

    private FollowUpBean mFollowUpBean;

    private EditText mEtContent;
    private EditText mEtRemark;

    private ProgressDialog mProgressDialog;
    private FollowUpEditPresenter mFollowUpEditPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followup_add);

        mFollowUpEditPresenter = new FollowUpEditPresenterImpl(this);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mFollowUpBean = (FollowUpBean) getIntent().getSerializableExtra("followUp");

        mEtContent = (EditText) findViewById(R.id.etContent);
        mEtRemark = (EditText) findViewById(R.id.etRemark);
        mEtContent.setText(mFollowUpBean.getContent());
        mEtRemark.setText(mFollowUpBean.getFollowUpRemarks());
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
                mFollowUpEditPresenter.editFollowUp(mFollowUpBean);
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
        View view = findViewById(R.id.followup_add_layout);
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
