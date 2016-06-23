package top.kass.pocketoa.view;

public interface ProductEditView {

    void navigateToDetail();
    void showProgress();
    void hideProgress();
    void showFailMsg(String msg);
    void showImage(String url);

}
