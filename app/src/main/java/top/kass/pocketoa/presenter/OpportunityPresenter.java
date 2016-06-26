package top.kass.pocketoa.presenter;

public interface OpportunityPresenter {

    void loadOpportunities(int type, int staffId, int page);

    void loadOpportunities(int customerId, int page);

}
