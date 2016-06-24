package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.OpportunityBean;
import top.kass.pocketoa.model.OpportunityModel;
import top.kass.pocketoa.model.impl.OpportunityModelImpl;
import top.kass.pocketoa.presenter.OpportunityDetailPresenter;
import top.kass.pocketoa.view.OpportunityDetailView;

public class OpportunityDetailPresenterImpl implements OpportunityDetailPresenter,
    OpportunityModel.OnLoadOpportunityListener,
    OpportunityModel.OnSingleOpportunityListener {

    private OpportunityDetailView mOpportunityDetailView;
    private OpportunityModel mOpportunityModel;

    public OpportunityDetailPresenterImpl(OpportunityDetailView opportunityDetailView) {
        this.mOpportunityDetailView = opportunityDetailView;
        this.mOpportunityModel = new OpportunityModelImpl();
    }

    @Override
    public void onLoadSuccess(OpportunityBean opportunityBean) {
        mOpportunityDetailView.hideProgress();
        mOpportunityDetailView.loadOpportunity(opportunityBean);
    }

    @Override
    public void onLoadFailure(String msg) {
        mOpportunityDetailView.hideProgress();
        mOpportunityDetailView.showFailMsg(msg);
    }

    @Override
    public void onSuccess() {
        mOpportunityDetailView.hideProgress();
        mOpportunityDetailView.navigateToMain(2);
    }

    @Override
    public void onFailure(String msg) {
        mOpportunityDetailView.hideProgress();
        mOpportunityDetailView.showFailMsg(msg);
    }

    @Override
    public void loadOpportunity(int opportunityId) {
        mOpportunityDetailView.showProgress("正在加载...");
        mOpportunityModel.loadOpportunity(opportunityId, this);
    }

    @Override
    public void deleteOpportunity(int opportunityId) {
        mOpportunityDetailView.showProgress("正在删除...");
        mOpportunityModel.deleteOpportunity(opportunityId, this);
    }

}
