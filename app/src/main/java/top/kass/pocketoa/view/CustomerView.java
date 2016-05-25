package top.kass.pocketoa.view;

import java.util.List;

import top.kass.pocketoa.bean.CustomerBean;

public interface CustomerView {

    void showProgress();
    void addCustomers(List<CustomerBean> customerList);
    void hideProgress();
    void showLoadFailMsg();

}
