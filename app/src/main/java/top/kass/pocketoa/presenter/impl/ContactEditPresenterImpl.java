package top.kass.pocketoa.presenter.impl;

import java.util.List;

import top.kass.pocketoa.bean.ContactBean;
import top.kass.pocketoa.bean.CustomerBean;
import top.kass.pocketoa.model.ContactModel;
import top.kass.pocketoa.model.CustomerModel;
import top.kass.pocketoa.model.impl.ContactModelImpl;
import top.kass.pocketoa.model.impl.CustomerModelImpl;
import top.kass.pocketoa.presenter.ContactEditPresenter;
import top.kass.pocketoa.view.ContactEditView;

public class ContactEditPresenterImpl implements ContactEditPresenter, ContactModel.OnSingleContactListener,
        CustomerModel.OnLoadCustomersListener {

    private ContactEditView mContactEditView;
    private ContactModel mContactModel;
    private CustomerModel mCustomerModel;

    public ContactEditPresenterImpl(ContactEditView contactEditView) {
        this.mContactEditView = contactEditView;
        this.mContactModel = new ContactModelImpl();
        this.mCustomerModel = new CustomerModelImpl();
    }

    @Override
    public void editContact(ContactBean contactBean) {
        mContactEditView.showProgress();
        mContactModel.saveContact(contactBean, this);
    }

    @Override
    public void loadCustomers() {
        mContactEditView.showProgress();
        mCustomerModel.loadCustomers(1, 0, -1, this);
    }


    @Override
    public void onSuccess() {
        mContactEditView.hideProgress();
        mContactEditView.navigateToDetail();
    }

    @Override
    public void onSuccess(List<CustomerBean> list) {
        mContactEditView.hideProgress();
        mContactEditView.loadCustomers(list);
    }

    @Override
    public void onFailure(String msg) {
        mContactEditView.hideProgress();
        mContactEditView.showFailMsg(msg);
    }
}
