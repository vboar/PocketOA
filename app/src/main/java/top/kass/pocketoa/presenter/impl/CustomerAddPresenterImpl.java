package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.CustomerBean;
import top.kass.pocketoa.model.CustomerModel;
import top.kass.pocketoa.model.impl.CustomerModelImpl;
import top.kass.pocketoa.presenter.CustomerAddPresenter;
import top.kass.pocketoa.view.CustomerAddView;

public class CustomerAddPresenterImpl implements CustomerAddPresenter, CustomerModel.OnSingleCustomerListener {

    private CustomerAddView mCustomerAddView;
    private CustomerModel mCustomerModel;

    public CustomerAddPresenterImpl(CustomerAddView customerAddView) {
        this.mCustomerAddView = customerAddView;
        this.mCustomerModel = new CustomerModelImpl();
    }

    @Override
    public void addCustomer(CustomerBean customerBean) {
        mCustomerAddView.showProgress();
        mCustomerModel.addCustomer(customerBean, this);
    }

    @Override
    public void onSuccess() {
        mCustomerAddView.hideProgress();
        mCustomerAddView.navigateToMain();
    }

    @Override
    public void onFailure(String msg) {
        mCustomerAddView.hideProgress();
        mCustomerAddView.showFailMsg(msg);
    }

}
