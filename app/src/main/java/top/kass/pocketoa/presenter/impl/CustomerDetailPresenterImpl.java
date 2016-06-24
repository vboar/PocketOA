package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.CustomerBean;
import top.kass.pocketoa.model.CustomerModel;
import top.kass.pocketoa.model.impl.CustomerModelImpl;
import top.kass.pocketoa.presenter.CustomerDetailPresenter;
import top.kass.pocketoa.view.CustomerDetailView;

public class CustomerDetailPresenterImpl implements CustomerDetailPresenter,
    CustomerModel.OnLoadCustomerListener,
    CustomerModel.OnSingleCustomerListener {

    private CustomerDetailView mCustomerDetailView;
    private CustomerModel mCustomerModel;

    public CustomerDetailPresenterImpl(CustomerDetailView customerDetailView) {
        this.mCustomerDetailView = customerDetailView;
        this.mCustomerModel = new CustomerModelImpl();
    }

    @Override
    public void loadCustomer(int customerId) {
        mCustomerDetailView.showProgress("正在加载...");
        mCustomerModel.loadCustomer(customerId, this);
    }

    @Override
    public void deleteCustomer(int customerId) {
        mCustomerDetailView.showProgress("正在删除...");
        mCustomerModel.deleteCustomer(customerId, this);
    }

    @Override
    public void onLoadSuccess(CustomerBean customerBean) {
        mCustomerDetailView.hideProgress();
        mCustomerDetailView.loadCustomer(customerBean);
    }

    @Override
    public void onLoadFailure(String msg) {
        mCustomerDetailView.hideProgress();
        mCustomerDetailView.showFailMsg(msg);
    }

    @Override
    public void onSuccess() {
        mCustomerDetailView.hideProgress();
        mCustomerDetailView.navigateToMain(2);
    }

    @Override
    public void onFailure(String msg) {
        mCustomerDetailView.hideProgress();
        mCustomerDetailView.showFailMsg(msg);
    }
}
