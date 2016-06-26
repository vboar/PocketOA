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

import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.ContractBean;
import top.kass.pocketoa.presenter.ContractEditPresenter;
import top.kass.pocketoa.presenter.impl.ContractEditPresenterImpl;
import top.kass.pocketoa.util.ToolsUtil;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.view.ContractEditView;

public class ContractEditActivity extends AppCompatActivity implements ContractEditView {

    private Toolbar mToolBar;
    private ProgressDialog mProgressDialog;
    private ContractEditPresenter mContractEditPresenter;
    private ContractBean mContractBean;

    private EditText mEtTitle;
    private EditText mEtCustomer;
    private EditText mEtOpportunity;
    private EditText mEtAmount;
    private EditText mEtStartDate;
    private EditText mEtEndDate;
    private EditText mEtNumber;
    private EditText mEtPayMethod;
    private EditText mEtCC;
    private EditText mEtOC;
    private EditText mEtSigningDate;
    private EditText mEtAttachment;
    private EditText mEtRemark;
    private MaterialSpinner mSpType;
    private MaterialSpinner mSpStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_add);

        mContractEditPresenter = new ContractEditPresenterImpl(this);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mContractBean = (ContractBean) getIntent().getSerializableExtra("contract");

        mEtTitle = (EditText) findViewById(R.id.etTitle);
        mEtCustomer = (EditText) findViewById(R.id.etCustomer);
        mEtOpportunity = (EditText) findViewById(R.id.etOpportunity);
        mEtAmount = (EditText) findViewById(R.id.etAmount);
        mEtStartDate = (EditText) findViewById(R.id.etStartDate);
        mEtEndDate = (EditText) findViewById(R.id.etEndDate);
        mEtNumber = (EditText) findViewById(R.id.etNumber);
        mEtPayMethod = (EditText) findViewById(R.id.etPayMethod);
        mEtCC = (EditText) findViewById(R.id.etCC);
        mEtOC = (EditText) findViewById(R.id.etOC);
        mEtSigningDate = (EditText) findViewById(R.id.etSigningDate);
        mEtAttachment = (EditText) findViewById(R.id.etAttachment);
        mEtRemark = (EditText) findViewById(R.id.etRemark);

        mEtTitle.setText(mContractBean.getContractTitle());
        mEtCustomer.setText(mContractBean.getCustomerId().toString());
        mEtOpportunity.setText(mContractBean.getOpportunityId().toString());
        if (mContractBean.getTotalAmount() != null) {
            mEtAmount.setText(mContractBean.getTotalAmount().toString());
        }
        mEtNumber.setText(mContractBean.getContractNumber());
        mEtPayMethod.setText(mContractBean.getPayMethod());
        mEtCC.setText(mContractBean.getClientContractor());
        mEtOC.setText(mContractBean.getOurContractor());
        mEtAttachment.setText(mContractBean.getAttachment());
        mEtRemark.setText(mContractBean.getContractRemarks());

        if (mContractBean.getStartDate().equals("")) {
            mEtStartDate.setText(ToolsUtil.getCurrentDate());
        } else {
            mEtStartDate.setText(mContractBean.getStartDate().substring(0, 10));
        }
        if (mContractBean.getEndDate().equals("")) {
            mEtEndDate.setText(ToolsUtil.getCurrentDate());
        } else {
            mEtEndDate.setText(mContractBean.getEndDate().substring(0, 10));
        }
        if (mContractBean.getSigningDate().equals("")) {
            mEtSigningDate.setText(ToolsUtil.getCurrentDate());
        } else {
            mEtSigningDate.setText(mContractBean.getSigningDate().substring(0, 10));
        }

        mSpType = (MaterialSpinner) findViewById(R.id.spType);
        mSpType.setItems(
                getString(R.string.contract_type_1),
                getString(R.string.contract_type_2),
                getString(R.string.contract_type_3)
        );
        mSpType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mContractBean.setContractType(position+1);
            }
        });
        if (mContractBean.getContractType() != null && mContractBean.getContractType() > 0) {
            mSpType.setSelectedIndex(mContractBean.getContractType()-1);
        } else {
            mSpType.setSelectedIndex(1);
        }

        mSpStatus = (MaterialSpinner) findViewById(R.id.spStatus);
        mSpStatus.setItems(
                getString(R.string.contract_status_1),
                getString(R.string.contract_status_2),
                getString(R.string.contract_status_3),
                getString(R.string.contract_status_4)
        );
        mSpStatus.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mContractBean.setContractStatus(position+1);
            }
        });
        if (mContractBean.getContractStatus() != null && mContractBean.getContractStatus() > 0) {
            mSpStatus.setSelectedIndex(mContractBean.getContractStatus()-1);
        } else {
            mSpStatus.setSelectedIndex(1);
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
                mContractBean.setContractTitle(mEtTitle.getText().toString());
                mContractBean.setCustomerId(Integer.parseInt(mEtCustomer.getText().toString()));
                mContractBean.setOpportunityId(Integer.parseInt(mEtOpportunity.getText().toString()));
                if (mEtAmount.getText().toString().equals("")) {
                    mContractBean.setTotalAmount(0.0);
                } else {
                    mContractBean.setTotalAmount(Double.parseDouble(mEtAmount.getText().toString()));
                }
                mContractBean.setStartDate(mEtStartDate.getText().toString());
                mContractBean.setEndDate(mEtEndDate.getText().toString());
                mContractBean.setContractNumber(mEtNumber.getText().toString());
                mContractBean.setPayMethod(mEtPayMethod.getText().toString());
                mContractBean.setClientContractor(mEtCC.getText().toString());
                mContractBean.setOurContractor(mEtOC.getText().toString());
                mContractBean.setSigningDate(mEtSigningDate.getText().toString());
                mContractBean.setAttachment(mEtAttachment.getText().toString());
                mContractBean.setContractRemarks(mEtRemark.getText().toString());
                mContractEditPresenter.editContract(mContractBean);
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
        View view = findViewById(R.id.opportunity_add_layout);
        UIUtil.showSnackBar(view, msg, Snackbar.LENGTH_SHORT);
    }

}
