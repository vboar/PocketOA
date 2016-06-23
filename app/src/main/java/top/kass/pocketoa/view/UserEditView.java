package top.kass.pocketoa.view;

import top.kass.pocketoa.bean.StaffBean;

public interface UserEditView {

    void setStaffBean(StaffBean staffBean);
    void navigateToMain();
    void showProgress(String msg);
    void hideProgress();
    void showFailMsg(String msg);
    void showImage(String url);

}
