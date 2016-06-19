package top.kass.pocketoa.model;

import java.util.List;

import top.kass.pocketoa.bean.ProductBean;

public interface ProductModel {

    void loadProducts(int page, OnLoadProductsListener listener);

    void loadProduct(int productId, OnLoadProductListner listener);

    void addProduct(ProductBean productBean, OnSingleProductListener listener);

    void saveProduct(ProductBean productBean, OnSingleProductListener listener);

    void deleteProduct(int productId, OnSingleProductListener listener);

    interface OnLoadProductsListener {
        void onSuccess(List<ProductBean> list);
        void onFailure(String msg, Exception e);
    }

    interface OnSingleProductListener {
        void onSuccess();
        void onFailure(String msg);
    }

    interface OnLoadProductListner {
        void onLoadSuccess(ProductBean productBean);
        void onLoadFailure(String msg);
    }


}
