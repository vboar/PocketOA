package top.kass.pocketoa.model;

import top.kass.pocketoa.model.impl.ProductModelImpl;

public interface ProductModel {

    void loadProducts(int page, ProductModelImpl.OnLoadProductsListener listener);

}
