package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.ProductBean;
import top.kass.pocketoa.model.ProductModel;
import top.kass.pocketoa.model.impl.ProductModelImpl;
import top.kass.pocketoa.presenter.ProductDetailPresenter;
import top.kass.pocketoa.view.ProductDetailView;

public class ProductDetailPresenterImpl implements ProductDetailPresenter,
        ProductModel.OnSingleProductListener,
        ProductModel.OnLoadProductListner {

    private ProductDetailView mProductDetailView;
    private ProductModel mProductModel;

    public ProductDetailPresenterImpl(ProductDetailView productDetailView) {
        this.mProductDetailView = productDetailView;
        this.mProductModel = new ProductModelImpl();
    }

    @Override
    public void loadProduct(int productId) {
        mProductDetailView.showProgress("正在加载...");
        mProductModel.loadProduct(productId, this);
    }

    @Override
    public void deleteProduct(int productId) {
        mProductDetailView.showProgress("正在删除...");
        mProductModel.deleteProduct(productId, this);
    }


    @Override
    public void onSuccess() {
        mProductDetailView.hideProgress();
        mProductDetailView.navigateToMain(2);
    }

    @Override
    public void onFailure(String msg) {
        mProductDetailView.hideProgress();
        mProductDetailView.showFailMsg(msg);
    }

    @Override
    public void onLoadSuccess(ProductBean productBean) {
        mProductDetailView.hideProgress();
        mProductDetailView.loadProduct(productBean);
    }

    @Override
    public void onLoadFailure(String msg) {
        mProductDetailView.hideProgress();
        mProductDetailView.showFailMsg(msg);
    }

}
