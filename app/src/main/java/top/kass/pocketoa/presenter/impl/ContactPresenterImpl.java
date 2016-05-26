package top.kass.pocketoa.presenter.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import top.kass.pocketoa.bean.ContactBean;
import top.kass.pocketoa.bean.CustomerBean;
import top.kass.pocketoa.model.CustomerModel;
import top.kass.pocketoa.model.impl.CustomerModelImpl;
import top.kass.pocketoa.presenter.ContactPresenter;
import top.kass.pocketoa.presenter.CustomerPresenter;
import top.kass.pocketoa.view.ContactView;
import top.kass.pocketoa.view.CustomerView;

public class ContactPresenterImpl implements ContactPresenter {

    private ContactView mContactView;

    public ContactPresenterImpl(ContactView contactView) {
        this.mContactView = contactView;
    }

    @Override
    public void loadContacts(final int type, final int pageIndex) {
        if(pageIndex == 0) {
            mContactView.showProgress();
        }
        mContactView.hideProgress();
        // TODO
        List<ContactBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ContactBean contactBean = new ContactBean();
            contactBean.setName("刘钦");
            contactBean.setCustomer("南京大学");
            list.add(contactBean);
        }
        if (pageIndex > 5) {
            mContactView.addContacts(new ArrayList<ContactBean>());
        } else {
            mContactView.addContacts(list);
        }
    }


}
