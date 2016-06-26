package top.kass.pocketoa.view;

import java.util.List;

import top.kass.pocketoa.bean.CustomerBean;

public interface ContactAddView {

    void navigateToMain();
    void loadCustomers(List<CustomerBean> list);
    void showProgress();
    void hideProgress();
    void showFailMsg(String msg);
}
