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

public class CustomerPresenterImpl implements CustomerPresenter {

    private CustomerView mCustomerView;
    private CustomerModel mCustomerModel;

    public CustomerPresenterImpl(CustomerView customerView) {
        this.mCustomerView = customerView;
        this.mCustomerModel = new CustomerModelImpl();
    }

    @Override
    public void loadCustomers(final int type, final int pageIndex) {
        if(pageIndex == 0) {
            mCustomerView.showProgress();
        }
        mCustomerView.hideProgress();
        // TODO
        List<CustomerBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CustomerBean customerBean = new CustomerBean();
            customerBean.setCustomerName("南京大学");
            customerBean.setCustomerType(new Random().nextInt(3) + 1);
            customerBean.setCustomerStatus(new Random().nextInt(5) + 1);
            list.add(customerBean);
        }
        if (pageIndex > 5) {
            mCustomerView.addCustomers(new ArrayList<CustomerBean>());
        } else {
            mCustomerView.addCustomers(list);
        }
    }


}
