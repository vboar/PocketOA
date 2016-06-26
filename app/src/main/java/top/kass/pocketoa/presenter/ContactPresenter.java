package top.kass.pocketoa.presenter;

public interface ContactPresenter {

    void loadContacts(int type, int staffId, int page);

    void loadContacts(int customerId, int page);

}
