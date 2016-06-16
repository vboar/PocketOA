package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.R;
import top.kass.pocketoa.presenter.MainPresenter;
import top.kass.pocketoa.view.MainView;

public class MainPresenterImpl implements MainPresenter {

    private MainView mMainView;

    public MainPresenterImpl(MainView mainView) {
        this.mMainView = mainView;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.nav_home:
                mMainView.switchToHome();
                break;
            case R.id.nav_customer:
                mMainView.switchToCustomer();
                break;
            case R.id.nav_contact:
                mMainView.switchToContact();
                break;
            case R.id.nav_opportunity:
                mMainView.switchToOpportunity();
                break;
            case R.id.nav_contract:
                mMainView.switchToContract();
                break;
            case R.id.nav_product:
                mMainView.switchToProduct();
                break;
            case R.id.nav_business:
                mMainView.switchToBusiness();
                break;
            case R.id.nav_logout:
                mMainView.switchToLogout();
                break;
        }
    }

}
