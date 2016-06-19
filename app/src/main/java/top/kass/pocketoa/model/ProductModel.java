package top.kass.pocketoa.model;

import java.util.List;

import top.kass.pocketoa.bean.ProductBean;

public interface ProductModel {

    void loadProducts(int page, OnLoadProductsListener listener);

    void addProduct(ProductBean productBean, OnSingleProductListener listener);

    void saveProduct(ProductBean productBean, OnSingleProductListener listener);

    void deleteProduct(ProductBean productBean, OnSingleProductListener listener);

    interface OnLoadProductsListener {
        void onSuccess(List<ProductBean> list);
        void onFailure(String msg, Exception e);
    }

    interface OnSingleProductListener {
        void onSuccess();
        void onFailure(String msg);
    }


}
