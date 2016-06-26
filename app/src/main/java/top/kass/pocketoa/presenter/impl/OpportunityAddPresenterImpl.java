package top.kass.pocketoa.presenter.impl;

import java.util.List;

import top.kass.pocketoa.bean.CustomerBean;
import top.kass.pocketoa.bean.OpportunityBean;
import top.kass.pocketoa.model.CustomerModel;
import top.kass.pocketoa.model.OpportunityModel;
import top.kass.pocketoa.model.impl.CustomerModelImpl;
import top.kass.pocketoa.model.impl.OpportunityModelImpl;
import top.kass.pocketoa.presenter.OpportunityAddPresenter;
import top.kass.pocketoa.view.OpportunityAddView;

public class OpportunityAddPresenterImpl implements OpportunityAddPresenter,
        OpportunityModel.OnSingleOpportunityListener,
        CustomerModel.OnLoadCustomersListener {

    private OpportunityAddView mOpportunityAddView;
    private OpportunityModel mOpportunityModel;
    private CustomerModel mCustomerModel;

    public OpportunityAddPresenterImpl(OpportunityAddView opportunityAddView) {
        this.mOpportunityAddView = opportunityAddView;
        this.mOpportunityModel = new OpportunityModelImpl();
        this.mCustomerModel = new CustomerModelImpl();
    }

    @Override
    public void onSuccess() {
        mOpportunityAddView.hideProgress();
        mOpportunityAddView.navigateToMain();
    }

    @Override
    public void onSuccess(List<CustomerBean> list) {
        mOpportunityAddView.hideProgress();
        mOpportunityAddView.loadCustomers(list);
    }

    @Override
    public void onFailure(String msg) {
        mOpportunityAddView.hideProgress();
        mOpportunityAddView.showFailMsg(msg);
    }

    @Override
    public void addOpportunity(OpportunityBean opportunityBean) {
        mOpportunityAddView.showProgress();
        mOpportunityModel.addOpportunity(opportunityBean, this);
    }

    @Override
    public void loadCustomers() {
        mOpportunityAddView.showProgress();
        mCustomerModel.loadCustomers(1, 0, -1, this);
    }

}
