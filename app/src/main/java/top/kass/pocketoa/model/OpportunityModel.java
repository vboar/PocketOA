package top.kass.pocketoa.model;

import java.util.List;

import top.kass.pocketoa.bean.OpportunityBean;

public interface OpportunityModel {

    void loadOpportunities(int type, int staffId, int page, OnLoadOpportunitiesListener listener);

    void loadOpportunitiesBySource(int sourceId, int sourceType, int page, OnLoadOpportunitiesListener listener);

    void loadOpportunity(int opportunityId, OnLoadOpportunityListener listener);

    void addOpportunity(OpportunityBean opportunityBean, OnSingleOpportunityListener listener);

    void saveOpportunity(OpportunityBean opportunityBean, OnSingleOpportunityListener listener);

    void deleteOpportunity(int opportunityId, OnSingleOpportunityListener listener);

    interface OnLoadOpportunitiesListener {
        void onSuccess(List<OpportunityBean> list);
        void onFailure(String msg);
    }

    interface OnSingleOpportunityListener {
        void onSuccess();
        void onFailure(String msg);
    }

    interface OnLoadOpportunityListener {
        void onLoadSuccess(OpportunityBean opportunityBean);
        void onLoadFailure(String msg);
    }

}
