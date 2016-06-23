package top.kass.pocketoa.model;

import java.util.List;

import top.kass.pocketoa.bean.CustomerBean;

public interface CustomerModel {

    void loadCustomers(int type, int staffId, int page, OnLoadCustomersListener listener);

    void loadCustomer(int customerId, OnLoadCustomerListener listener);

    void addCustomer(CustomerBean customerBean, OnSingleCustomerListener listener);

    void saveCustomer(CustomerBean customerBean, OnSingleCustomerListener listener);

    void deleteCustomer(int customerId, OnSingleCustomerListener listener);

    interface OnLoadCustomersListener {
        void onSuccess(List<CustomerBean> list);
        void onFailure(String msg);
    }

    interface OnSingleCustomerListener {
        void onSuccess();
        void onFailure(String msg);
    }

    interface OnLoadCustomerListener {
        void onLoadSuccess(CustomerBean customerBean);
        void onLoadFailure(String msg);
    }

}
