package top.kass.pocketoa.presenter.impl;

import java.util.List;

import top.kass.pocketoa.bean.ContractBean;
import top.kass.pocketoa.model.ContractModel;
import top.kass.pocketoa.model.impl.ContractModelImpl;
import top.kass.pocketoa.presenter.ContractPresenter;
import top.kass.pocketoa.view.ContractView;

public class ContractPresenterImpl implements ContractPresenter, ContractModel.OnLoadContractsListener {

    private ContractView mContractView;
    private ContractModel mContractModel;

    public ContractPresenterImpl(ContractView contractView) {
        this.mContractView = contractView;
        this.mContractModel = new ContractModelImpl();
    }

    @Override
    public void loadContracts(int type, int staffId, int page) {
        if (page == 0) {
            mContractView.showProgress();
        }
        mContractModel.loadContracts(type, staffId, page, this);
    }

    @Override
    public void loadContractsBySource(int sourceId, int sourceType, int page) {
        if (page == 0) {
            mContractView.showProgress();
        }
        mContractModel.loadContractsBySource(sourceId, sourceType, page, this);
    }

    @Override
    public void onSuccess(List<ContractBean> list) {
        mContractView.hideProgress();
        mContractView.addContracts(list);
    }

    @Override
    public void onFailure(String msg) {
        mContractView.hideProgress();
        mContractView.showLoadFailMsg();
    }

}
