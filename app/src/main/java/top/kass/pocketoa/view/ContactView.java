package top.kass.pocketoa.view;

import java.util.List;

import top.kass.pocketoa.bean.ContactBean;
import top.kass.pocketoa.bean.CustomerBean;

public interface ContactView {

    void showProgress();
    void addContacts(List<ContactBean> contactList);
    void hideProgress();
    void showLoadFailMsg();

}
