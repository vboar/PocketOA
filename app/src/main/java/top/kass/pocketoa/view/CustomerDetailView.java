package top.kass.pocketoa.view;

import top.kass.pocketoa.bean.CustomerBean;

public interface CustomerDetailView {

    void loadCustomer(CustomerBean customerBean);
    void navigateToEdit();
    // type 编辑1 删除2
    void navigateToMain(int type);
    void showProgress(String msg);
    void hideProgress();
    void showFailMsg(String msg);

}
