package top.kass.pocketoa.presenter.impl;

import android.util.Log;

import top.kass.pocketoa.R;
import top.kass.pocketoa.presenter.MainPresenter;
import top.kass.pocketoa.view.MainView;

public class MainPresenterImpl implements MainPresenter {

    private MainView mainView;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.nav_home:
                mainView.switchToHome();
                break;
            case R.id.nav_customer:
                mainView.switchToCustomer();
                break;
            case R.id.nav_contact:
                mainView.switchToContact();
                break;
            case R.id.nav_opportunity:
                mainView.switchToOpportunity();
                break;
            case R.id.nav_contract:
                mainView.switchToContract();
                break;
            case R.id.nav_product:
                mainView.switchToProduct();
                break;
            case R.id.nav_business:
                mainView.switchToBusiness();
                break;
            case R.id.nav_logout:
                mainView.switchToLogout();
                break;
        }
    }

}
