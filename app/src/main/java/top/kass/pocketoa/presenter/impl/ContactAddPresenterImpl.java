package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.ContactBean;
import top.kass.pocketoa.model.ContactModel;
import top.kass.pocketoa.model.impl.ContactModelImpl;
import top.kass.pocketoa.presenter.ContactAddPresenter;
import top.kass.pocketoa.view.ContactAddView;

public class ContactAddPresenterImpl implements ContactAddPresenter, ContactModel.OnSingleContactListener {

    private ContactAddView mContactAddView;
    private ContactModel mContactModel;

    public ContactAddPresenterImpl(ContactAddView contactAddView) {
        this.mContactAddView = contactAddView;
        this.mContactModel = new ContactModelImpl();
    }

    @Override
    public void addContact(ContactBean contactBean) {
        mContactAddView.showProgress();
        mContactModel.addContact(contactBean, this);
    }

    @Override
    public void onSuccess() {
        mContactAddView.hideProgress();
        mContactAddView.navigateToMain();
    }

    @Override
    public void onFailure(String msg) {
        mContactAddView.hideProgress();
        mContactAddView.showFailMsg(msg);
    }
}
