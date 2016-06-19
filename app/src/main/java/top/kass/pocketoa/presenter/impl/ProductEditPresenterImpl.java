package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.ProductBean;
import top.kass.pocketoa.model.ProductModel;
import top.kass.pocketoa.model.impl.ProductModelImpl;
import top.kass.pocketoa.presenter.ProductEditPresenter;
import top.kass.pocketoa.view.ProductEditView;

public class ProductEditPresenterImpl implements ProductEditPresenter, ProductModel.OnSingleProductListener {

    private ProductEditView mProductEditView;
    private ProductModel mProductModel;

    public ProductEditPresenterImpl(ProductEditView productEditView) {
        this.mProductEditView = productEditView;
        this.mProductModel = new ProductModelImpl();
    }

    @Override
    public void editProduct(ProductBean productBean) {
        mProductEditView.showProgress();
        mProductModel.saveProduct(productBean, this);
    }

    @Override
    public void onSuccess() {
        mProductEditView.hideProgress();
        mProductEditView.navigateToDetail();
    }

    @Override
    public void onFailure(String msg) {
        mProductEditView.hideProgress();
        mProductEditView.showFailMsg(msg);
    }
}
