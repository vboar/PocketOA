package top.kass.pocketoa.view;

import top.kass.pocketoa.bean.ContactBean;

public interface ContactDetailView {

    void loadContact(ContactBean contactBean);
    void navigateToEdit();
    // type 编辑1 删除2
    void navigateToMain(int type);
    void showProgress(String msg);
    void hideProgress();
    void showFailMsg(String msg);

}
