package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.ContractBean;
import top.kass.pocketoa.model.ContractModel;
import top.kass.pocketoa.model.impl.ContractModelImpl;
import top.kass.pocketoa.presenter.ContractAddPresenter;
import top.kass.pocketoa.view.ContractAddView;

public class ContractAddPresenterImpl implements ContractAddPresenter, ContractModel.OnSingleContractListener {

    private ContractAddView mContractAddView;
    private ContractModel mContractModel;

    public ContractAddPresenterImpl(ContractAddView contractAddView) {
        this.mContractAddView = contractAddView;
        this.mContractModel = new ContractModelImpl();
    }

    @Override
    public void addContract(ContractBean contractBean) {
        mContractAddView.showProgress();
        mContractModel.addContract(contractBean, this);
    }

    @Override
    public void onSuccess() {
        mContractAddView.hideProgress();
        mContractAddView.navigateToMain();
    }

    @Override
    public void onFailure(String msg) {
        mContractAddView.hideProgress();
        mContractAddView.showFailMsg(msg);
    }
}
