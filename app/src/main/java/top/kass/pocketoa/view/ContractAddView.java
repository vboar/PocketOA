package top.kass.pocketoa.view;

import java.util.List;

import top.kass.pocketoa.bean.CustomerBean;
import top.kass.pocketoa.bean.OpportunityBean;

public interface ContractAddView {

    void navigateToMain();
    void loadCustomers(List<CustomerBean> list);
    void loadOpportunities(List<OpportunityBean> list);
    void showProgress();
    void hideProgress();
    void showFailMsg(String msg);

}
