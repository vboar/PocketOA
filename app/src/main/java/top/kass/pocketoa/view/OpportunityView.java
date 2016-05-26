package top.kass.pocketoa.view;

import java.util.List;

import top.kass.pocketoa.bean.OpportunityBean;
import top.kass.pocketoa.bean.ProductBean;

public interface OpportunityView {

    void showProgress();
    void addOpportunities(List<OpportunityBean> opportunityList);
    void hideProgress();
    void showLoadFailMsg();

}
