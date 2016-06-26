package top.kass.pocketoa.view;

import top.kass.pocketoa.bean.FollowUpBean;

public interface FollowUpDetailView {

    void loadFollowUp(FollowUpBean followUpBean);
    void navigateToEdit();
    // type 编辑1 删除2
    void navigateToList(int type);
    void showProgress(String msg);
    void hideProgress();
    void showFailMsg(String msg);

}
