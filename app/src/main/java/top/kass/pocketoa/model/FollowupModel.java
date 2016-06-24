package top.kass.pocketoa.model;

import java.util.List;

import top.kass.pocketoa.bean.FollowUpBean;

public interface FollowUpModel {

    void loadFollowUps(int type, int sourceId, int page, OnLoadFollowUpsListener listener);

    void loadFollowUp(int followupId, OnLoadFollowUpListener listener);

    void addFollowUp(FollowUpBean followUpBean, OnSingleFollowUpListener listener);

    void saveFollowUp(FollowUpBean followUpBean, OnSingleFollowUpListener listener);

    void deleteFollowUp(int followUpId, OnSingleFollowUpListener listener);

    interface OnLoadFollowUpsListener {
        void onSuccess(List<FollowUpBean> list);
        void onFailure(String msg);
    }

    interface OnSingleFollowUpListener {
        void onSuccess();
        void onFailure(String msg);
    }

    interface OnLoadFollowUpListener {
        void onLoadSuccess(FollowUpBean followUpBean);
        void onLoadFailure(String msg);
    }


}
