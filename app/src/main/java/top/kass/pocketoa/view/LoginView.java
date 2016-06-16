package top.kass.pocketoa.view;

import top.kass.pocketoa.bean.StaffBean;

public interface LoginView {

    void navigateToMain(StaffBean staffBean);
    void navigateToRegister();
    void showProgress();
    void hideProgress();
    void showFailMsg(String msg);
    void hideInput();
}
