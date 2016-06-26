package top.kass.pocketoa.presenter.impl;

import java.util.List;

import top.kass.pocketoa.bean.ContactBean;
import top.kass.pocketoa.model.ContactModel;
import top.kass.pocketoa.model.impl.ContactModelImpl;
import top.kass.pocketoa.presenter.ContactPresenter;
import top.kass.pocketoa.view.ContactView;

public class ContactPresenterImpl implements ContactPresenter, ContactModel.OnLoadContactsListener {

    private ContactView mContactView;
    private ContactModel mContactModel;

    public ContactPresenterImpl(ContactView contactView) {
        this.mContactView = contactView;
        this.mContactModel = new ContactModelImpl();
    }

    @Override
    public void loadContacts(final int type, final int staffId, final int pageIndex) {
        if(pageIndex == 0) {
            mContactView.showProgress();
        }
        mContactModel.loadContacts(type, staffId, pageIndex, this);
    }

    @Override
    public void loadContacts(int customerId, int page) {
        if(page == 0) {
            mContactView.showProgress();
        }
        mContactModel.loadContactsBySource(customerId, 1, page, this);
    }


    @Override
    public void onSuccess(List<ContactBean> list) {
        mContactView.hideProgress();
        mContactView.addContacts(list);
    }

    @Override
    public void onFailure(String msg) {
        mContactView.hideProgress();
        mContactView.showLoadFailMsg();
    }
}
