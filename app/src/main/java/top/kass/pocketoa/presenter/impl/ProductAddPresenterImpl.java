package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.ProductBean;
import top.kass.pocketoa.model.ProductModel;
import top.kass.pocketoa.model.impl.ProductModelImpl;
import top.kass.pocketoa.presenter.ProductAddPresenter;
import top.kass.pocketoa.view.ProductAddView;

public class ProductAddPresenterImpl implements ProductAddPresenter, ProductModel.OnSingleProductListener {

    private ProductAddView mProductAddView;
    private ProductModel mProductModel;

    public ProductAddPresenterImpl(ProductAddView productAddView) {
        this.mProductAddView = productAddView;
        this.mProductModel = new ProductModelImpl();
    }

    @Override
    public void addProduct(ProductBean productBean) {
        mProductAddView.showProgress();
        mProductModel.addProduct(productBean, this);
    }

    @Override
    public void onSuccess() {
        mProductAddView.hideProgress();
        mProductAddView.navigateToMain();
    }

    @Override
    public void onFailure(String msg) {
        mProductAddView.hideProgress();
        mProductAddView.showFailMsg(msg);
    }
}
