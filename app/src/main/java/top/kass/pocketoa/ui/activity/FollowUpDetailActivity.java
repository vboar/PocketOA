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

import com.umeng.analytics.MobclickAgent;

import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.FollowUpBean;
import top.kass.pocketoa.presenter.FollowUpDetailPresenter;
import top.kass.pocketoa.presenter.impl.FollowUpDetailPresenterImpl;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.view.FollowUpDetailView;

public class FollowUpDetailActivity extends AppCompatActivity implements FollowUpDetailView {

    private Toolbar mToolBar;
    private FollowUpBean mFollowUpBean;
    private ProgressDialog mProgressDialog;
    private FollowUpDetailPresenter mFollowUpDetailPresenter;
    private boolean isEdited = false;
    private String staffName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followup_detail);

        mFollowUpDetailPresenter = new FollowUpDetailPresenterImpl(this);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToList(1);
            }
        });

        FollowUpBean followUpBean = (FollowUpBean) getIntent().getSerializableExtra("followUp");
        staffName = followUpBean.getStaff().getName();
        mFollowUpDetailPresenter.loadFollowUp(followUpBean.getFollowUpId());
    }

    @Override
    public void onBackPressed() {
        navigateToList(1);
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
                new AlertDialog.Builder(this).setTitle("确定要删除该跟进记录吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mFollowUpDetailPresenter.deleteFollowUp(mFollowUpBean.getFollowUpId());
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
    public void loadFollowUp(FollowUpBean followUpBean) {
        mFollowUpBean = followUpBean;

        TextView tvDate = (TextView) findViewById(R.id.tvDate);
        TextView tvStaff = (TextView) findViewById(R.id.tvStaff);
        TextView tvContent = (TextView) findViewById(R.id.tvContent);
        TextView tvRemark = (TextView) findViewById(R.id.tvRemark);
        tvDate.setText(mFollowUpBean.getCreateTime().substring(0, 10));
        tvStaff.setText(staffName);
        tvContent.setText(mFollowUpBean.getContent());
        tvRemark.setText("备注： " + mFollowUpBean.getFollowUpRemarks());
    }

    @Override
    public void navigateToEdit() {
        Intent intent = new Intent(this, FollowUpEditActivity.class);
        intent.putExtra("followUp", mFollowUpBean);
        startActivityForResult(intent, 1);
    }

    @Override
    public void navigateToList(int type) {
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
        View view = findViewById(R.id.followup_detail_layout);
        UIUtil.showSnackBar(view, msg, Snackbar.LENGTH_SHORT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 1) {
            isEdited = true;
            mFollowUpDetailPresenter.loadFollowUp(mFollowUpBean.getFollowUpId());
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
