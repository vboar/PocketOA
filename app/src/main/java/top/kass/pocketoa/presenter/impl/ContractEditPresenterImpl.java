package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.ContractBean;
import top.kass.pocketoa.model.ContractModel;
import top.kass.pocketoa.model.impl.ContractModelImpl;
import top.kass.pocketoa.presenter.ContractEditPresenter;
import top.kass.pocketoa.view.ContractEditView;

public class ContractEditPresenterImpl implements ContractEditPresenter,
        ContractModel.OnSingleContractListener {

    private ContractEditView mContractEditView;
    private ContractModel mContractModel;

    public ContractEditPresenterImpl(ContractEditView contractEditView) {
        this.mContractEditView = contractEditView;
        this.mContractModel = new ContractModelImpl();
    }

    @Override
    public void editContract(ContractBean contractBean) {
        mContractEditView.showProgress();
        mContractModel.saveContract(contractBean, this);
    }

    @Override
    public void onSuccess() {
        mContractEditView.hideProgress();
        mContractEditView.navigateToDetail();
    }

    @Override
    public void onFailure(String msg) {
        mContractEditView.hideProgress();
        mContractEditView.showFailMsg(msg);
    }

}
