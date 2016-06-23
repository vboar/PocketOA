package top.kass.pocketoa.presenter;

import top.kass.pocketoa.bean.StaffBean;

public interface UserEditPresenter {

    void loadStaff(int userId);

    void editStaff(StaffBean staffBean);

    void uploadImage(String path);

}
