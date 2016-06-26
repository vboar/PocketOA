package top.kass.pocketoa.presenter.impl;

import java.util.List;

import top.kass.pocketoa.bean.OpportunityBean;
import top.kass.pocketoa.model.OpportunityModel;
import top.kass.pocketoa.model.impl.OpportunityModelImpl;
import top.kass.pocketoa.presenter.OpportunityPresenter;
import top.kass.pocketoa.view.OpportunityView;

public class OpportunityPresenterImpl implements OpportunityPresenter,
        OpportunityModel.OnLoadOpportunitiesListener {

    private OpportunityView mOpportunityView;
    private OpportunityModel mOpportunityModel;

    public OpportunityPresenterImpl(OpportunityView opportunityView) {
        this.mOpportunityView = opportunityView;
        this.mOpportunityModel = new OpportunityModelImpl();
    }

    @Override
    public void loadOpportunities(final int type, final int staffId, final int pageIndex) {
        if(pageIndex == 0) {
            mOpportunityView.showProgress();
        }
        mOpportunityModel.loadOpportunities(type, staffId, pageIndex, this);
    }

    @Override
    public void loadOpportunities(int customerId, int page) {
        if(page == 0) {
            mOpportunityView.showProgress();
        }
        mOpportunityModel.loadOpportunitiesBySource(customerId, 1, page, this);
    }


    @Override
    public void onSuccess(List<OpportunityBean> list) {
        mOpportunityView.hideProgress();
        mOpportunityView.addOpportunities(list);
    }

    @Override
    public void onFailure(String msg) {
        mOpportunityView.hideProgress();
        mOpportunityView.showLoadFailMsg();
    }

}
