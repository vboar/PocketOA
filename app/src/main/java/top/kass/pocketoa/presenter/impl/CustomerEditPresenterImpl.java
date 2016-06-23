package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.CustomerBean;
import top.kass.pocketoa.model.CustomerModel;
import top.kass.pocketoa.model.impl.CustomerModelImpl;
import top.kass.pocketoa.presenter.CustomerEditPresenter;
import top.kass.pocketoa.view.CustomerEditView;

public class CustomerEditPresenterImpl implements CustomerEditPresenter,
        CustomerModel.OnSingleCustomerListener {

    private CustomerEditView mCustomerEditView;
    private CustomerModel mCustomerModel;

    public CustomerEditPresenterImpl(CustomerEditView customerEditView) {
        this.mCustomerEditView = customerEditView;
        this.mCustomerModel = new CustomerModelImpl();
    }

    @Override
    public void editCustomer(CustomerBean customerBean) {
        mCustomerEditView.showProgress();
        mCustomerModel.saveCustomer(customerBean, this);
    }

    @Override
    public void onSuccess() {
        mCustomerEditView.hideProgress();
        mCustomerEditView.navigateToDetail();
    }

    @Override
    public void onFailure(String msg) {
        mCustomerEditView.hideProgress();
        mCustomerEditView.showFailMsg(msg);
    }

}
