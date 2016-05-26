package top.kass.pocketoa.view;

import java.util.List;

import top.kass.pocketoa.bean.ContactBean;
import top.kass.pocketoa.bean.ProductBean;

public interface ProductView {

    void showProgress();
    void addProducts(List<ProductBean> productList);
    void hideProgress();
    void showLoadFailMsg();

}
