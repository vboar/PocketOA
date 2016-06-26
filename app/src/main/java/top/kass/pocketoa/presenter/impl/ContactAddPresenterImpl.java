package top.kass.pocketoa.presenter.impl;

import java.util.List;

import top.kass.pocketoa.bean.ContactBean;
import top.kass.pocketoa.bean.CustomerBean;
import top.kass.pocketoa.model.ContactModel;
import top.kass.pocketoa.model.CustomerModel;
import top.kass.pocketoa.model.impl.ContactModelImpl;
import top.kass.pocketoa.model.impl.CustomerModelImpl;
import top.kass.pocketoa.presenter.ContactAddPresenter;
import top.kass.pocketoa.view.ContactAddView;

public class ContactAddPresenterImpl implements ContactAddPresenter, ContactModel.OnSingleContactListener,
        CustomerModel.OnLoadCustomersListener {

    private ContactAddView mContactAddView;
    private ContactModel mContactModel;
    private CustomerModel mCustomerModel;

    public ContactAddPresenterImpl(ContactAddView contactAddView) {
        this.mContactAddView = contactAddView;
        this.mContactModel = new ContactModelImpl();
        this.mCustomerModel = new CustomerModelImpl();
    }

    @Override
    public void addContact(ContactBean contactBean) {
        mContactAddView.showProgress();
        mContactModel.addContact(contactBean, this);
    }

    @Override
    public void loadCustomers() {
        mContactAddView.showProgress();
        mCustomerModel.loadCustomers(1, 0, -1, this);
    }

    @Override
    public void onSuccess() {
        mContactAddView.hideProgress();
        mContactAddView.navigateToMain();
    }
    @Override
    public void onSuccess(List<CustomerBean> list) {
        mContactAddView.hideProgress();
        mContactAddView.loadCustomers(list);
    }

    @Override
    public void onFailure(String msg) {
        mContactAddView.hideProgress();
        mContactAddView.showFailMsg(msg);
    }
}
