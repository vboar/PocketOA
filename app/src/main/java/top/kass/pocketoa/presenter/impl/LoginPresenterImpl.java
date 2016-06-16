package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.StaffBean;
import top.kass.pocketoa.model.StaffModel;
import top.kass.pocketoa.model.impl.StaffModelImpl;
import top.kass.pocketoa.presenter.LoginPresenter;
import top.kass.pocketoa.view.LoginView;

public class LoginPresenterImpl implements LoginPresenter, StaffModel.OnLoginFinishedListener {

    private LoginView mLoginView;
    private StaffModel mStaffModel;

    public LoginPresenterImpl(LoginView loginView) {
        this.mLoginView = loginView;
        this.mStaffModel = new StaffModelImpl();
    }

    @Override
    public void login(String userId) {
        mLoginView.hideInput();
        mLoginView.showProgress();
        mStaffModel.login(userId, this);
    }

    @Override
    public void onSuccess(StaffBean staffBean) {
        mLoginView.hideProgress();
        mLoginView.navigateToMain(staffBean);
    }

    @Override
    public void onFailure(String msg) {
        mLoginView.hideProgress();
        mLoginView.showFailMsg(msg);
    }

}
