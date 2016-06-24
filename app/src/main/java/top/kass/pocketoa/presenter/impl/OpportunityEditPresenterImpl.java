package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.OpportunityBean;
import top.kass.pocketoa.model.OpportunityModel;
import top.kass.pocketoa.model.impl.OpportunityModelImpl;
import top.kass.pocketoa.presenter.OpportunityEditPresenter;
import top.kass.pocketoa.view.OpportunityEditView;

public class OpportunityEditPresenterImpl implements OpportunityEditPresenter,
        OpportunityModel.OnSingleOpportunityListener {

    private OpportunityEditView mOpportunityEditView;
    private OpportunityModel mOpportunityModel;

    public OpportunityEditPresenterImpl(OpportunityEditView opportunityEditView) {
        this.mOpportunityEditView = opportunityEditView;
        this.mOpportunityModel = new OpportunityModelImpl();
    }

    @Override
    public void editOpportunity(OpportunityBean opportunityBean) {
        mOpportunityEditView.showProgress();
        mOpportunityModel.saveOpportunity(opportunityBean, this);
    }

    @Override
    public void onSuccess() {
        mOpportunityEditView.hideProgress();
        mOpportunityEditView.navigateToDetail();
    }

    @Override
    public void onFailure(String msg) {
        mOpportunityEditView.hideProgress();
        mOpportunityEditView.showFailMsg(msg);
    }
}
