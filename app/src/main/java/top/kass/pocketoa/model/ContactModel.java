package top.kass.pocketoa.model;

import java.util.List;

import top.kass.pocketoa.bean.ContactBean;

public interface ContactModel {

    void loadContacts(int type, int staffId, int page, OnLoadContactsListener listener);

    void loadContact(int contactId, OnLoadProductListner listener);

    void addContact(ContactBean contactBean, OnSingleProductListener listener);

    void saveContact(ContactBean contactBean, OnSingleProductListener listener);

    void deleteContact(int contactId, OnSingleProductListener listener);

    interface OnLoadContactsListener {
        void onSuccess(List<ContactBean> list);
        void onFailure(String msg);
    }

    interface OnSingleProductListener {
        void onSuccess();
        void onFailure(String msg);
    }

    interface OnLoadProductListner {
        void onLoadSuccess(ContactBean contactBean);
        void onLoadFailure(String msg);
    }

}
