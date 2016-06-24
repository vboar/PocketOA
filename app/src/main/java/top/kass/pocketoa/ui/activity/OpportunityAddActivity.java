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

import com.jaredrummler.materialspinner.MaterialSpinner;

import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.OpportunityBean;
import top.kass.pocketoa.presenter.OpportunityAddPresenter;
import top.kass.pocketoa.presenter.impl.OpportunityAddPresenterImpl;
import top.kass.pocketoa.util.ToolsUtil;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.view.OpportunityAddView;

public class OpportunityAddActivity extends AppCompatActivity implements OpportunityAddView {

    private Toolbar mToolBar;
    private ProgressDialog mProgressDialog;
    private OpportunityAddPresenter mOpportunityAddPresenter;

    private EditText mEtTitle;
    private EditText mEtCustomer;
    private EditText mEtAmount;
    private EditText mEtSource;
    private EditText mEtChannel;
    private EditText mEtADate;
    private EditText mEtEDate;
    private EditText mEtRemark;
    private MaterialSpinner mSpType;
    private MaterialSpinner mSpStatus;

    private OpportunityBean mOpportunityBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opportunity_add);

        mOpportunityAddPresenter = new OpportunityAddPresenterImpl(this);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEtTitle = (EditText) findViewById(R.id.etTitle);
        mEtCustomer = (EditText) findViewById(R.id.etCustomer);
        mEtAmount = (EditText) findViewById(R.id.etAmount);
        mEtSource = (EditText) findViewById(R.id.etSource);
        mEtChannel = (EditText) findViewById(R.id.etChannel);
        mEtADate = (EditText) findViewById(R.id.etADate);
        mEtEDate = (EditText) findViewById(R.id.etEDate);
        mEtRemark = (EditText) findViewById(R.id.etRemark);

        mEtADate.setText(ToolsUtil.getCurrentDate());
        mEtEDate.setText(ToolsUtil.getCurrentDate());

        mSpType = (MaterialSpinner) findViewById(R.id.spType);
        mSpType.setItems(
                getString(R.string.opportunity_type_important),
                getString(R.string.opportunity_type_normal)
        );
        mSpType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mOpportunityBean.setBusinessType(position+1);
            }
        });

        mSpStatus = (MaterialSpinner) findViewById(R.id.spStatus);
        mSpStatus.setItems(
                getString(R.string.opportunity_status_1),
                getString(R.string.opportunity_status_2),
                getString(R.string.opportunity_status_3),
                getString(R.string.opportunity_status_4),
                getString(R.string.opportunity_status_5),
                getString(R.string.opportunity_status_6)
        );
        mSpStatus.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mOpportunityBean.setOpportunityStatus(position+1);
            }
        });

        mOpportunityBean = new OpportunityBean();
        mOpportunityBean.setBusinessType(1);
        mOpportunityBean.setOpportunityStatus(1);

        SharedPreferences sharedPreferences = getSharedPreferences("oa", MODE_PRIVATE);
        int staffId =  sharedPreferences.getInt("staffId", 0);
        mOpportunityBean.setStaffId(staffId);
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
                mOpportunityBean.setOpportunityTitle(mEtTitle.getText().toString());
                mOpportunityBean.setCustomerId(Integer.parseInt(mEtCustomer.getText().toString()));
                if (mEtAmount.getText().toString().equals("")) {
                    mOpportunityBean.setEstimatedAmount(0.0);
                } else {
                    mOpportunityBean.setEstimatedAmount(Double.parseDouble(
                            mEtAmount.getText().toString()));
                }
                mOpportunityBean.setOpportunitiesSource(mEtSource.getText().toString());
                mOpportunityBean.setChannel(mEtChannel.getText().toString());
                mOpportunityBean.setAcquisitionDate(mEtADate.getText().toString());
                mOpportunityBean.setExpectedDate(mEtEDate.getText().toString());
                mOpportunityBean.setOpportunityRemarks(mEtRemark.getText().toString());
                mOpportunityAddPresenter.addOpportunity(mOpportunityBean);
                break;
        }
        return false;
    }

    @Override
    public void navigateToMain() {
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
        View view = findViewById(R.id.opportunity_add_layout);
        UIUtil.showSnackBar(view, msg, Snackbar.LENGTH_SHORT);
    }

}
