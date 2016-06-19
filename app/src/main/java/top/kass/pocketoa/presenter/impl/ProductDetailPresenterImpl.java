package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.ProductBean;
import top.kass.pocketoa.model.ProductModel;
import top.kass.pocketoa.model.impl.ProductModelImpl;
import top.kass.pocketoa.presenter.ProductDetailPresenter;
import top.kass.pocketoa.view.ProductDetailView;

public class ProductDetailPresenterImpl implements ProductDetailPresenter, ProductModel.OnSingleProductListener {

    private ProductDetailView mProductDetailView;
    private ProductModel mProductModel;

    public ProductDetailPresenterImpl(ProductDetailView productDetailView) {
        this.mProductDetailView = productDetailView;
        this.mProductModel = new ProductModelImpl();
    }

    @Override
    public void deleteProduct(ProductBean productBean) {
        mProductDetailView.showProgress();
        mProductModel.deleteProduct(productBean, this);
    }


    @Override
    public void onSuccess() {
        mProductDetailView.hideProgress();
        mProductDetailView.navigateToMain();
    }

    @Override
    public void onFailure(String msg) {
        mProductDetailView.hideProgress();
        mProductDetailView.showFailMsg(msg);
    }

}
