package top.kass.pocketoa.view;

import top.kass.pocketoa.bean.StaffBean;

public interface MainView {

    void switchToHome();
    void switchToCustomer();
    void switchToContact();
    void switchToOpportunity();
    void switchToContract();
    void switchToProduct();
    void switchToBusiness();
    void switchToLogout();
    void reloadStaffInfo(StaffBean staffBean);
    void navigateToUserEdit();

}
