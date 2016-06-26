package top.kass.pocketoa.model;

import java.util.List;

import top.kass.pocketoa.bean.ContractBean;

public interface ContractModel {

    void loadContracts(int type, int staffId, int page, OnLoadContractsListener listener);

    void loadContractsBySource(int sourceId, int sourceType, int page, OnLoadContractsListener listener);

    void loadContract(int contractId, OnLoadContractListener listner);

    void addContract(ContractBean contractBean, OnSingleContractListener listener);

    void saveContract(ContractBean contractBean, OnSingleContractListener listener);

    void deleteContract(int contractId, OnSingleContractListener listener);

    interface OnLoadContractsListener {
        void onSuccess(List<ContractBean> list);
        void onFailure(String msg);
    }

    interface OnSingleContractListener {
        void onSuccess();
        void onFailure(String msg);
    }

    interface OnLoadContractListener {
        void onLoadSuccess(ContractBean contractBean);
        void onLoadFailure(String msg);
    }

}
