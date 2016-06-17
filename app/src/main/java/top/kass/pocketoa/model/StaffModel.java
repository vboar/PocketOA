package top.kass.pocketoa.model;

import top.kass.pocketoa.bean.StaffBean;

public interface StaffModel {

    void login(String userId, OnLoginFinishedListener listener);

    void save(StaffBean staffBean, OnSaveListener listener);

    interface OnLoginFinishedListener {
        void onSuccess(StaffBean staffBean);
        void onFailure(String msg);
    }

    interface OnSaveListener {
        void onSaveSuccess();
        void onSaveFailure();
    }

}
