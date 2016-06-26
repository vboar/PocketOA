package top.kass.pocketoa.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.OpportunityBean;
import top.kass.pocketoa.presenter.OpportunityDetailPresenter;
import top.kass.pocketoa.presenter.impl.OpportunityDetailPresenterImpl;
import top.kass.pocketoa.util.ToolsUtil;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.view.OpportunityDetailView;

public class OpportunityDetailActivity extends AppCompatActivity implements OpportunityDetailView {

    private Toolbar mToolBar;
    private boolean canEdited = false;
    private boolean isEdited = false;
    private OpportunityBean mOpportunityBean;
    private String customerName;
    private OpportunityDetailPresenter mOpportunityDetailPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opportunity_detail);

        mOpportunityDetailPresenter = new OpportunityDetailPresenterImpl(this);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMain(1);
            }
        });

        final OpportunityBean opportunityBean = (OpportunityBean) getIntent().getSerializableExtra("opportunity");
        customerName = opportunityBean.getCustomer().getCustomerName();

        Button btnFollowup = (Button) findViewById(R.id.btnFollowup);
        assert btnFollowup != null;
        btnFollowup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpportunityDetailActivity.this, FollowUpActivity.class);
                intent.putExtra("sourceId", opportunityBean.getOpportunityId());
                intent.putExtra("sourceType", 2);
                startActivity(intent);
            }
        });
        Button btnProduct = (Button) findViewById(R.id.btnProduct);
        assert btnProduct != null;
        btnProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpportunityDetailActivity.this, ProductActivity.class);
                intent.putExtra("sourceId", opportunityBean.getOpportunityId());
                intent.putExtra("sourceType", 2);
                startActivity(intent);
            }
        });
        Button btnContract = (Button) findViewById(R.id.btnContract);
        assert btnContract != null;
        btnContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpportunityDetailActivity.this, ContractActivity.class);
                intent.putExtra("sourceId", opportunityBean.getOpportunityId());
                intent.putExtra("sourceType", 2);
                startActivity(intent);
            }
        });

        int staffId = getSharedPreferences("oa", MODE_PRIVATE).getInt("staffId", 0);
//        if (staffId == opportunityBean.getStaffId()) {
//            canEdited = true;
//        } else {
//            View btnGroup = (View) findViewById(R.id.btnGroup);
//            btnGroup.setVisibility(View.GONE);
//        }
        mOpportunityDetailPresenter.loadOpportunity(opportunityBean.getOpportunityId());
    }

    @Override
    public void onBackPressed() {
        navigateToMain(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        if (canEdited) {
//            getMenuInflater().inflate(R.menu.common_detail, menu);
//        }
        getMenuInflater().inflate(R.menu.common_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                new AlertDialog.Builder(this).setTitle("确定要删除该商机吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mOpportunityDetailPresenter.deleteOpportunity(mOpportunityBean.getOpportunityId());
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
    public void loadOpportunity(OpportunityBean opportunityBean) {
        mOpportunityBean = opportunityBean;
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        TextView tvCustomer = (TextView) findViewById(R.id.tvCustomer);
        TextView tvAmount = (TextView) findViewById(R.id.tvAmount);
        TextView tvType = (TextView) findViewById(R.id.tvType);
        TextView tvStatus = (TextView) findViewById(R.id.tvStatus);
        TextView tvSource = (TextView) findViewById(R.id.tvSource);
        TextView tvChannel = (TextView) findViewById(R.id.tvChannel);
        TextView tvADate = (TextView) findViewById(R.id.tvADate);
        TextView tvEDate = (TextView) findViewById(R.id.tvEDate);
        TextView tvRemark = (TextView) findViewById(R.id.tvRemark);
        tvTitle.setText(mOpportunityBean.getOpportunityTitle());
        tvCustomer.setText(customerName);
        if (mOpportunityBean.getEstimatedAmount() != null) {
            tvAmount.setText(mOpportunityBean.getEstimatedAmount().toString());
        }
        tvType.setText(OpportunityBean.getTypeString(mOpportunityBean.getBusinessType()));
        tvStatus.setText(OpportunityBean.getStatusString(mOpportunityBean.getOpportunityStatus()));
        tvSource.setText(mOpportunityBean.getOpportunitiesSource());
        tvChannel.setText(mOpportunityBean.getChannel());
        if (!mOpportunityBean.getAcquisitionDate().equals("")) {
            tvADate.setText(mOpportunityBean.getAcquisitionDate().substring(0, 10));
        }
        if(!mOpportunityBean.getExpectedDate().equals("")) {
            tvEDate.setText(mOpportunityBean.getExpectedDate().substring(0, 10));
        }
        tvRemark.setText(mOpportunityBean.getOpportunityRemarks());
    }

    @Override
    public void navigateToEdit() {
        Intent intent = new Intent(this, OpportunityEditActivity.class);
        intent.putExtra("opportunity", mOpportunityBean);
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
        View view = findViewById(R.id.opportunity_detail_layout);
        UIUtil.showSnackBar(view, msg, Snackbar.LENGTH_SHORT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 1) {
            isEdited = true;
            mOpportunityDetailPresenter.loadOpportunity(mOpportunityBean.getOpportunityId());
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
