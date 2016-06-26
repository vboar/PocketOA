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

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.List;

import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.CustomerBean;
import top.kass.pocketoa.bean.OpportunityBean;
import top.kass.pocketoa.presenter.OpportunityEditPresenter;
import top.kass.pocketoa.presenter.impl.OpportunityEditPresenterImpl;
import top.kass.pocketoa.util.ToolsUtil;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.view.OpportunityEditView;

public class OpportunityEditActivity extends AppCompatActivity implements OpportunityEditView {

    private Toolbar mToolBar;
    private ProgressDialog mProgressDialog;
    private OpportunityEditPresenter mOpportunityEditPresenter;
    private OpportunityBean mOpportunityBean;

    private EditText mEtTitle;
    private EditText mEtAmount;
    private EditText mEtSource;
    private EditText mEtChannel;
    private EditText mEtADate;
    private EditText mEtEDate;
    private EditText mEtRemark;
    private MaterialSpinner mSpType;
    private MaterialSpinner mSpStatus;
    private MaterialSpinner mSpCustomer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opportunity_add);

        mOpportunityEditPresenter = new OpportunityEditPresenterImpl(this);

        mSpCustomer = (MaterialSpinner) findViewById(R.id.spCustomer);
        mOpportunityEditPresenter.loadCustomers();

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mOpportunityBean = (OpportunityBean) getIntent().getSerializableExtra("opportunity");

        mEtTitle = (EditText) findViewById(R.id.etTitle);
        mEtAmount = (EditText) findViewById(R.id.etAmount);
        mEtSource = (EditText) findViewById(R.id.etSource);
        mEtChannel = (EditText) findViewById(R.id.etChannel);
        mEtADate = (EditText) findViewById(R.id.etADate);
        mEtEDate = (EditText) findViewById(R.id.etEDate);
        mEtRemark = (EditText) findViewById(R.id.etRemark);

        mEtTitle.setText(mOpportunityBean.getOpportunityTitle());
        if (mOpportunityBean.getEstimatedAmount() != null) {
            mEtAmount.setText(mOpportunityBean.getEstimatedAmount().toString());
        }
        mEtSource.setText(mOpportunityBean.getOpportunitiesSource());
        mEtChannel.setText(mOpportunityBean.getChannel());
        if (mOpportunityBean.getAcquisitionDate().equals("")) {
            mEtADate.setText(ToolsUtil.getCurrentDate());
        } else {
            mEtADate.setText(mOpportunityBean.getAcquisitionDate().substring(0, 10));
        }
        if (mOpportunityBean.getExpectedDate().equals("")) {
            mEtEDate.setText(ToolsUtil.getCurrentDate().substring(0, 10));
        } else {
            mEtEDate.setText(mOpportunityBean.getExpectedDate());
        }
        mEtRemark.setText(mOpportunityBean.getOpportunityRemarks());

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
        if (mOpportunityBean.getBusinessType() != null && mOpportunityBean.getBusinessType() > 0) {
            mSpType.setSelectedIndex(mOpportunityBean.getBusinessType()-1);
        } else {
            mOpportunityBean.setBusinessType(1);
        }

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
        if (mOpportunityBean.getOpportunityStatus() != null && mOpportunityBean.getOpportunityStatus() > 0) {
            mSpStatus.setSelectedIndex(mOpportunityBean.getOpportunityStatus()-1);
        } else {
            mOpportunityBean.setOpportunityStatus(1);
        }

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
                if (mEtAmount.getText().toString().equals("")) {
                    mOpportunityBean.setEstimatedAmount(0.0);
                } else {
                    mOpportunityBean.setEstimatedAmount(Double.parseDouble(mEtAmount.getText().toString()));
                }
                mOpportunityBean.setOpportunitiesSource(mEtSource.getText().toString());
                mOpportunityBean.setChannel(mEtChannel.getText().toString());
                mOpportunityBean.setAcquisitionDate(mEtADate.getText().toString());
                mOpportunityBean.setExpectedDate(mEtEDate.getText().toString());
                mOpportunityBean.setOpportunityRemarks(mEtRemark.getText().toString());
                mOpportunityEditPresenter.editOpportunity(mOpportunityBean);
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
    public void loadCustomers(List<CustomerBean> list) {
        final int[] cids = new int[list.size()];
        String[] cnames = new String[list.size()];
        int position = 0;
        for (int i = 0; i < list.size(); i++) {
            cids[i] = list.get(i).getCustomerId();
            cnames[i] = list.get(i).getCustomerName();

            if (mOpportunityBean.getCustomerId() == cids[i]) {
                position = i;
            }
        }
        mSpCustomer.setItems(cnames);
        mSpCustomer.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                mOpportunityBean.setCustomerId(cids[position]);
            }
        });
        mSpCustomer.setSelectedIndex(position);
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
        View view = findViewById(R.id.opportunity_add_layout);
        UIUtil.showSnackBar(view, msg, Snackbar.LENGTH_SHORT);
    }

}
