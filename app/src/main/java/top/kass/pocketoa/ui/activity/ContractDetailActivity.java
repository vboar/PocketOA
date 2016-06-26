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
import top.kass.pocketoa.bean.ContractBean;
import top.kass.pocketoa.presenter.ContractDetailPresenter;
import top.kass.pocketoa.presenter.impl.ContractDetailPresenterImpl;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.view.ContractDetailView;

public class ContractDetailActivity extends AppCompatActivity implements ContractDetailView {

    private Toolbar mToolBar;
    private boolean canEdited = false;
    private boolean isEdited = false;
    private ContractBean mContractBean;
    private String customerName;
    private String opportunityTitle;
    private ContractDetailPresenter mContractDetailPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_detail);

        mContractDetailPresenter = new ContractDetailPresenterImpl(this);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMain(1);
            }
        });

        final ContractBean contractBean = (ContractBean) getIntent().getSerializableExtra("contract");
        customerName = contractBean.getCustomer().getCustomerName();
        opportunityTitle = contractBean.getOpportunity().getOpportunityTitle();

        Button btnFollowup = (Button) findViewById(R.id.btnFollowup);
        assert btnFollowup != null;
        btnFollowup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContractDetailActivity.this, FollowUpActivity.class);
                intent.putExtra("sourceId", contractBean.getContractId());
                intent.putExtra("sourceType", 3);
                startActivity(intent);
            }
        });

        int staffId = getSharedPreferences("oa", MODE_PRIVATE).getInt("staffId", 0);
//        if (staffId == contractBean.getStaffId()) {
//            canEdited = true;
//        } else {
//            View btnGroup = (View) findViewById(R.id.btnGroup);
//            btnGroup.setVisibility(View.GONE);
//        }
        mContractDetailPresenter.loadContract(contractBean.getContractId());
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
                new AlertDialog.Builder(this).setTitle("确定要删除该合同吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mContractDetailPresenter.deleteContract(mContractBean.getContractId());
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
    public void loadContract(ContractBean contractBean) {
        mContractBean = contractBean;
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        TextView tvCustomer = (TextView) findViewById(R.id.tvCustomer);
        TextView tvOpportunity = (TextView) findViewById(R.id.tvOpportunity);
        TextView tvAmount = (TextView) findViewById(R.id.tvAmount);
        TextView tvType = (TextView) findViewById(R.id.tvType);
        TextView tvStatus = (TextView) findViewById(R.id.tvStatus);
        TextView tvStartDate = (TextView) findViewById(R.id.tvStartDate);
        TextView tvEndDate = (TextView) findViewById(R.id.tvEndDate);
        TextView tvNumber = (TextView) findViewById(R.id.tvNumber);
        TextView tvPayMethod = (TextView) findViewById(R.id.tvPayMethod);
        TextView tvCC = (TextView) findViewById(R.id.tvCC);
        TextView tvOC = (TextView) findViewById(R.id.tvOC);
        TextView tvSigningDate = (TextView) findViewById(R.id.tvSigningDate);
        TextView tvAttachment = (TextView) findViewById(R.id.tvAttachment);
        TextView tvRemark = (TextView) findViewById(R.id.tvRemark);
        tvTitle.setText(mContractBean.getContractTitle());
        tvCustomer.setText(customerName);
        tvOpportunity.setText(opportunityTitle);
        if (mContractBean.getTotalAmount() != null) {
            tvAmount.setText(mContractBean.getTotalAmount().toString());
        }
        tvType.setText(ContractBean.getTypeString(mContractBean.getContractType()));
        tvStatus.setText(ContractBean.getStatusString(mContractBean.getContractStatus()));
        if (!mContractBean.getStartDate().equals("")) {
            tvStartDate.setText(mContractBean.getStartDate().substring(0, 10));
        }
        if (!mContractBean.getEndDate().equals("")) {
            tvEndDate.setText(mContractBean.getEndDate().substring(0, 10));
        }
        if (!mContractBean.getSigningDate().equals("")) {
            tvSigningDate.setText(mContractBean.getSigningDate().substring(0, 10));
        }
        tvNumber.setText(mContractBean.getContractNumber());
        tvPayMethod.setText(mContractBean.getPayMethod());
        tvCC.setText(mContractBean.getClientContractor());
        tvOC.setText(mContractBean.getOurContractor());
        tvAttachment.setText(mContractBean.getAttachment());
        tvRemark.setText(mContractBean.getContractRemarks());
    }

    @Override
    public void navigateToEdit() {
        Intent intent = new Intent(this, ContractEditActivity.class);
        intent.putExtra("contract", mContractBean);
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
        View view = findViewById(R.id.contract_detail_layout);
        UIUtil.showSnackBar(view, msg, Snackbar.LENGTH_SHORT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 1) {
            isEdited = true;
            mContractDetailPresenter.loadContract(mContractBean.getContractId());
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
