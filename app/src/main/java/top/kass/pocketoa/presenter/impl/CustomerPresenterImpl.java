package top.kass.pocketoa.presenter.impl;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import top.kass.pocketoa.bean.CustomerBean;
import top.kass.pocketoa.model.CustomerModel;
import top.kass.pocketoa.model.impl.CustomerModelImpl;
import top.kass.pocketoa.presenter.CustomerPresenter;
import top.kass.pocketoa.view.CustomerView;

public class CustomerPresenterImpl implements CustomerPresenter, CustomerModel.OnLoadCustomersListener {

    private CustomerView mCustomerView;
    private CustomerModel mCustomerModel;

    public CustomerPresenterImpl(CustomerView customerView) {
        this.mCustomerView = customerView;
        this.mCustomerModel = new CustomerModelImpl();
    }

    @Override
    public void loadCustomers(final int type, final int staffId, final int pageIndex) {
        if(pageIndex == 0) {
            mCustomerView.showProgress();
        }
        mCustomerModel.loadCustomers(type, staffId, pageIndex, this);
    }


    @Override
    public void onSuccess(List<CustomerBean> list) {
        mCustomerView.hideProgress();
        mCustomerView.addCustomers(list);
    }

    @Override
    public void onFailure(String msg) {
        mCustomerView.hideProgress();
        mCustomerView.showLoadFailMsg();
    }
}
