package top.kass.pocketoa.presenter.impl;

import java.util.List;

import top.kass.pocketoa.bean.CustomerBean;
import top.kass.pocketoa.bean.OpportunityBean;
import top.kass.pocketoa.model.CustomerModel;
import top.kass.pocketoa.model.OpportunityModel;
import top.kass.pocketoa.model.impl.CustomerModelImpl;
import top.kass.pocketoa.model.impl.OpportunityModelImpl;
import top.kass.pocketoa.presenter.OpportunityEditPresenter;
import top.kass.pocketoa.view.OpportunityEditView;

public class OpportunityEditPresenterImpl implements OpportunityEditPresenter,
        OpportunityModel.OnSingleOpportunityListener,
        CustomerModel.OnLoadCustomersListener {

    private OpportunityEditView mOpportunityEditView;
    private OpportunityModel mOpportunityModel;
    private CustomerModel mCustomerModel;

    public OpportunityEditPresenterImpl(OpportunityEditView opportunityEditView) {
        this.mOpportunityEditView = opportunityEditView;
        this.mOpportunityModel = new OpportunityModelImpl();
        this.mCustomerModel = new CustomerModelImpl();
    }

    @Override
    public void editOpportunity(OpportunityBean opportunityBean) {
        mOpportunityEditView.showProgress();
        mOpportunityModel.saveOpportunity(opportunityBean, this);
    }

    @Override
    public void loadCustomers() {
        mOpportunityEditView.showProgress();
        mCustomerModel.loadCustomers(1, 0, -1, this);
    }

    @Override
    public void onSuccess() {
        mOpportunityEditView.hideProgress();
        mOpportunityEditView.navigateToDetail();
    }

    @Override
    public void onSuccess(List<CustomerBean> list) {
        mOpportunityEditView.hideProgress();
        mOpportunityEditView.loadCustomers(list);
    }

    @Override
    public void onFailure(String msg) {
        mOpportunityEditView.hideProgress();
        mOpportunityEditView.showFailMsg(msg);
    }
}
