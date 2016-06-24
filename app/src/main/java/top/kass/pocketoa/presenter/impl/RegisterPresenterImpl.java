package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.StaffBean;
import top.kass.pocketoa.model.StaffModel;
import top.kass.pocketoa.model.impl.StaffModelImpl;
import top.kass.pocketoa.presenter.RegisterPresenter;
import top.kass.pocketoa.view.RegisterView;

public class RegisterPresenterImpl implements RegisterPresenter, StaffModel.OnAddListener {

    private RegisterView mRegisterView;
    private StaffModel mStaffModel;

    public RegisterPresenterImpl(RegisterView registerView) {
        this.mRegisterView = registerView;
        this.mStaffModel = new StaffModelImpl();
    }

    @Override
    public void onSuccess() {
        mRegisterView.hideProgress();
        mRegisterView.navigateToLogin();
    }

    @Override
    public void onFailure(String msg) {
        mRegisterView.hideProgress();
        mRegisterView.showFailMsg(msg);
    }

    @Override
    public void register(StaffBean staffBean) {
        mRegisterView.showProgress();
        mStaffModel.add(staffBean, this);
    }
}
