package top.kass.pocketoa.presenter.impl;

import java.util.ArrayList;
import java.util.List;

import top.kass.pocketoa.bean.OpportunityBean;
import top.kass.pocketoa.presenter.OpportunityPresenter;
import top.kass.pocketoa.view.OpportunityView;

public class OpportunityPresenterImpl implements OpportunityPresenter {

    private OpportunityView mOpportunityView;

    public OpportunityPresenterImpl(OpportunityView opportunityView) {
        this.mOpportunityView = opportunityView;
    }

    @Override
    public void loadOpportunities(final int type, final int pageIndex) {
        if(pageIndex == 0) {
            mOpportunityView.showProgress();
        }
        mOpportunityView.hideProgress();
        // TODO
        List<OpportunityBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            OpportunityBean opportunityBean = new OpportunityBean();
            opportunityBean.setTitle("军火");
            opportunityBean.setType(1);
            opportunityBean.setCustomer("南京大学");
            opportunityBean.setStatus(1);
            opportunityBean.setAmount(12550);
            list.add(opportunityBean);
        }
        if (pageIndex > 5) {
            mOpportunityView.addOpportunities(new ArrayList<OpportunityBean>());
        } else {
            mOpportunityView.addOpportunities(list);
        }
    }


}
