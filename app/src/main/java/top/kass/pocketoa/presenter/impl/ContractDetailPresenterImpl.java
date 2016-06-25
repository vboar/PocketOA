package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.ContractBean;
import top.kass.pocketoa.model.ContractModel;
import top.kass.pocketoa.model.impl.ContractModelImpl;
import top.kass.pocketoa.presenter.ContractDetailPresenter;
import top.kass.pocketoa.view.ContractDetailView;

public class ContractDetailPresenterImpl implements ContractDetailPresenter,
        ContractModel.OnLoadContractListener,
        ContractModel.OnSingleContractListener {

    private ContractDetailView mContractDetailView;
    private ContractModel mContractModel;

    public ContractDetailPresenterImpl(ContractDetailView contractDetailView) {
        this.mContractDetailView = contractDetailView;
        this.mContractModel = new ContractModelImpl();
    }

    @Override
    public void loadContract(int contractId) {
        mContractDetailView.showProgress("正在加载...");
        mContractModel.loadContract(contractId, this);
    }

    @Override
    public void deleteContract(int contractId) {
        mContractDetailView.showProgress("正在删除...");
        mContractModel.deleteContract(contractId, this);
    }

    @Override
    public void onLoadSuccess(ContractBean contractBean) {
        mContractDetailView.hideProgress();
        mContractDetailView.loadContract(contractBean);
    }

    @Override
    public void onLoadFailure(String msg) {
        mContractDetailView.hideProgress();
        mContractDetailView.showFailMsg(msg);
    }

    @Override
    public void onSuccess() {
        mContractDetailView.hideProgress();
        mContractDetailView.navigateToMain(2);
    }

    @Override
    public void onFailure(String msg) {
        mContractDetailView.hideProgress();
        mContractDetailView.showFailMsg(msg);
    }

}
