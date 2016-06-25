package top.kass.pocketoa.model;

import java.util.List;

import top.kass.pocketoa.bean.ContactBean;

public interface ContactModel {

    void loadContacts(int type, int staffId, int page, OnLoadContactsListener listener);

    void loadContact(int contactId, OnLoadContactListener listener);

    void addContact(ContactBean contactBean, OnSingleContactListener listener);

    void saveContact(ContactBean contactBean, OnSingleContactListener listener);

    void deleteContact(int contactId, OnSingleContactListener listener);

    interface OnLoadContactsListener {
        void onSuccess(List<ContactBean> list);
        void onFailure(String msg);
    }

    interface OnSingleContactListener {
        void onSuccess();
        void onFailure(String msg);
    }

    interface OnLoadContactListener {
        void onLoadSuccess(ContactBean contactBean);
        void onLoadFailure(String msg);
    }

}
