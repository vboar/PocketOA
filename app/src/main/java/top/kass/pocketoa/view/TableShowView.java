package top.kass.pocketoa.view;

public interface TableShowView {

    void showProgress();
    void showTable(int[] data1, double[] data2);
    void hideProgress();
    void showLoadFailMsg();

}
