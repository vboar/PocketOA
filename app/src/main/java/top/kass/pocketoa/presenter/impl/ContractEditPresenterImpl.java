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
import top.kass.pocketoa.presenter.ContractEditPresenter;
import top.kass.pocketoa.view.ContractEditView;

public class ContractEditPresenterImpl implements ContractEditPresenter,
        ContractModel.OnSingleContractListener,
        CustomerModel.OnLoadCustomersListener,
        OpportunityModel.OnLoadOpportunitiesListener {

    private ContractEditView mContractEditView;
    private ContractModel mContractModel;
    private CustomerModel mCustomerModel;
    private OpportunityModel mOpportunityModel;

    public ContractEditPresenterImpl(ContractEditView contractEditView) {
        this.mContractEditView = contractEditView;
        this.mContractModel = new ContractModelImpl();
        this.mCustomerModel = new CustomerModelImpl();
        this.mOpportunityModel = new OpportunityModelImpl();
    }

    @Override
    public void editContract(ContractBean contractBean) {
        mContractEditView.showProgress();
        mContractModel.saveContract(contractBean, this);
    }

    @Override
    public void loadCustomers() {
        mContractEditView.showProgress();
        mCustomerModel.loadCustomers(1, 0, -1, this);
    }

    @Override
    public void loadOpportunities() {
        mOpportunityModel.loadOpportunities(1, 0, -1, this);
    }

    @Override
    public void onSuccess() {
        mContractEditView.hideProgress();
        mContractEditView.navigateToDetail();
    }

    @Override
    public void onSuccess(List<CustomerBean> list) {
        mContractEditView.hideProgress();
        mContractEditView.loadCustomers(list);
    }

    @Override
    public void onFailure(String msg) {
        mContractEditView.hideProgress();
        mContractEditView.showFailMsg(msg);
    }

    @Override
    public void onLoadSuccess(List<OpportunityBean> list) {
        mContractEditView.loadOpportunities(list);
    }

    @Override
    public void onLoadFailure(String msg) {
        mContractEditView.hideProgress();
        mContractEditView.showFailMsg(msg);
    }

}
