package top.kass.pocketoa.model;

import java.util.List;

import top.kass.pocketoa.bean.ContactBean;
import top.kass.pocketoa.bean.ContractBean;

public interface ContactModel {

    void loadContacts(int type, int staffId, int page, OnLoadContactsListener listener);

    void loadContact(int contactId, OnLoadContactListner listener);

    void addContact(ContactBean contactBean, OnSingleContactListener listener);

    void saveContact(ContactBean contactBean, OnSingleContactListener listener);

    void deleteContact(int contactId, OnSingleContactListener listener);

    interface OnLoadContactsListener {
        void onSuccess(List<ContractBean> list);
        void onFailure(String msg);
    }

    interface OnSingleContactListener {
        void onSuccess();
        void onFailure(String msg);
    }

    interface OnLoadContactListner {
        void onLoadSuccess(ContractBean contractBean);
        void onLoadFailure(String msg);
    }

}
