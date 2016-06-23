package top.kass.pocketoa.presenter.impl;

import android.util.Log;

import top.kass.pocketoa.bean.ContactBean;
import top.kass.pocketoa.model.ContactModel;
import top.kass.pocketoa.model.impl.ContactModelImpl;
import top.kass.pocketoa.presenter.ContactDetailPresenter;
import top.kass.pocketoa.view.ContactDetailView;

public class ContactDetailPresenterImpl implements ContactDetailPresenter,
        ContactModel.OnSingleContactListener,
        ContactModel.OnLoadContactListner {

    private ContactDetailView mContactDetailView;
    private ContactModel mContactModel;

    public ContactDetailPresenterImpl(ContactDetailView contactDetailView) {
        this.mContactDetailView = contactDetailView;
        this.mContactModel = new ContactModelImpl();
    }

    @Override
    public void loadContact(int contactId) {
        mContactDetailView.showProgress("正在加载...");
        mContactModel.loadContact(contactId, this);
    }

    @Override
    public void deleteContact(int contactId) {
        mContactDetailView.showProgress("正在删除...");
        mContactModel.deleteContact(contactId, this);
    }

    @Override
    public void onSuccess() {
        mContactDetailView.hideProgress();
        mContactDetailView.navigateToMain(2);
    }

    @Override
    public void onFailure(String msg) {
        mContactDetailView.hideProgress();
        mContactDetailView.showFailMsg(msg);
    }

    @Override
    public void onLoadSuccess(ContactBean contactBean) {
        mContactDetailView.hideProgress();
        mContactDetailView.loadContact(contactBean);
    }

    @Override
    public void onLoadFailure(String msg) {
        mContactDetailView.hideProgress();
        mContactDetailView.showFailMsg(msg);
    }

}
