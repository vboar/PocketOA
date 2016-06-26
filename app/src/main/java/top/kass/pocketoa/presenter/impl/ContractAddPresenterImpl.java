package top.kass.pocketoa.presenter.impl;

import java.util.List;

import top.kass.pocketoa.bean.ContractBean;
import top.kass.pocketoa.bean.CustomerBean;
import top.kass.pocketoa.bean.OpportunityBean;
import top.kass.pocketoa.model.ContractModel;
import top.kass.pocketoa.model.CustomerModel;
import top.kass.pocketoa.model.OpportunityModel;
import top.kass.pocketoa.model.impl.ContractModelImpl;
import top.kass.pocketoa.model.impl.CustomerModelImpl;
import top.kass.pocketoa.model.impl.OpportunityModelImpl;
import top.kass.pocketoa.presenter.ContractAddPresenter;
import top.kass.pocketoa.view.ContractAddView;

public class ContractAddPresenterImpl implements ContractAddPresenter,
        ContractModel.OnSingleContractListener,
        CustomerModel.OnLoadCustomersListener,
        OpportunityModel.OnLoadOpportunitiesListener {

    private ContractAddView mContractAddView;
    private ContractModel mContractModel;
    private CustomerModel mCustomerModel;
    private OpportunityModel mOpportunityModel;

    public ContractAddPresenterImpl(ContractAddView contractAddView) {
        this.mContractAddView = contractAddView;
        this.mContractModel = new ContractModelImpl();
        this.mCustomerModel = new CustomerModelImpl();
        this.mOpportunityModel = new OpportunityModelImpl();
    }

    @Override
    public void addContract(ContractBean contractBean) {
        mContractAddView.showProgress();
        mContractModel.addContract(contractBean, this);
    }

    @Override
    public void loadCustomers() {
        mContractAddView.showProgress();
        mCustomerModel.loadCustomers(1, 0, -1, this);
    }

    @Override
    public void loadOpportunities() {
        mOpportunityModel.loadOpportunities(1, 0, -1, this);
    }

    @Override
    public void onSuccess() {
        mContractAddView.hideProgress();
        mContractAddView.navigateToMain();
    }

    @Override
    public void onFailure(String msg) {
        mContractAddView.hideProgress();
        mContractAddView.showFailMsg(msg);
    }

    @Override
    public void onLoadSuccess(List<OpportunityBean> list) {
        mContractAddView.loadOpportunities(list);
    }

    @Override
    public void onLoadFailure(String msg) {
        mContractAddView.hideProgress();
        mContractAddView.showFailMsg(msg);
    }

    @Override
    public void onSuccess(List<CustomerBean> list) {
        mContractAddView.hideProgress();
        mContractAddView.loadCustomers(list);
    }

}
