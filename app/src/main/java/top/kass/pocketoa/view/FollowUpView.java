package top.kass.pocketoa.view;

import java.util.List;

import top.kass.pocketoa.bean.FollowUpBean;

public interface FollowUpView {

    void showProgress();
    void addFollowUps(List<FollowUpBean> followUpList);
    void hideProgress();
    void showLoadFailMsg();

}
