package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.ContactBean;
import top.kass.pocketoa.model.ContactModel;
import top.kass.pocketoa.model.impl.ContactModelImpl;
import top.kass.pocketoa.presenter.ContactEditPresenter;
import top.kass.pocketoa.view.ContactEditView;

public class ContactEditPresenterImpl implements ContactEditPresenter, ContactModel.OnSingleContactListener {

    private ContactEditView mContactEditView;
    private ContactModel mContactModel;

    public ContactEditPresenterImpl(ContactEditView contactEditView) {
        this.mContactEditView = contactEditView;
        this.mContactModel = new ContactModelImpl();
    }

    @Override
    public void editContact(ContactBean contactBean) {
        mContactEditView.showProgress();
        mContactModel.saveContact(contactBean, this);
    }


    @Override
    public void onSuccess() {
        mContactEditView.hideProgress();
        mContactEditView.navigateToDetail();
    }

    @Override
    public void onFailure(String msg) {
        mContactEditView.hideProgress();
        mContactEditView.showFailMsg(msg);
    }
}
