package top.kass.pocketoa.view;

import java.util.List;

import top.kass.pocketoa.bean.ContractBean;

public interface ContractView {

    void showProgress();
    void addContracts(List<ContractBean> contractList);
    void hideProgress();
    void showLoadFailMsg();

}
