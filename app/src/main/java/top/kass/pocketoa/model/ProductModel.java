package top.kass.pocketoa.model;

import java.util.List;

import top.kass.pocketoa.bean.ProductBean;

public interface ProductModel {

    void loadProducts(int page, OnLoadProductsListener listener);

    interface OnLoadProductsListener {
        void onSuccess(List<ProductBean> list);
        void onFailure(String msg, Exception e);
    }


}
