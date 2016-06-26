package top.kass.pocketoa.presenter;

public interface ContractPresenter {

    void loadContracts(int type, int staffId, int page);

    void loadContractsBySource(int sourceId, int sourceType, int page);

}
