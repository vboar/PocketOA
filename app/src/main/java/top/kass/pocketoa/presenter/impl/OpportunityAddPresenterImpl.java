package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.OpportunityBean;
import top.kass.pocketoa.model.OpportunityModel;
import top.kass.pocketoa.model.impl.OpportunityModelImpl;
import top.kass.pocketoa.presenter.OpportunityAddPresenter;
import top.kass.pocketoa.view.OpportunityAddView;

public class OpportunityAddPresenterImpl implements OpportunityAddPresenter, OpportunityModel.OnSingleOpportunityListener {

    private OpportunityAddView mOpportunityAddView;
    private OpportunityModel mOpportunityModel;

    public OpportunityAddPresenterImpl(OpportunityAddView opportunityAddView) {
        this.mOpportunityAddView = opportunityAddView;
        this.mOpportunityModel = new OpportunityModelImpl();
    }

    @Override
    public void onSuccess() {
        mOpportunityAddView.hideProgress();
        mOpportunityAddView.navigateToMain();
    }

    @Override
    public void onFailure(String msg) {
        mOpportunityAddView.hideProgress();
        mOpportunityAddView.showFailMsg(msg);
    }

    @Override
    public void addOpportunity(OpportunityBean opportunityBean) {
        mOpportunityAddView.showProgress();
        mOpportunityModel.addOpportunity(opportunityBean, this);
    }

}
