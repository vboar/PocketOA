package top.kass.pocketoa.presenter.impl;

import java.util.List;

import top.kass.pocketoa.bean.ProductBean;
import top.kass.pocketoa.model.ProductModel;
import top.kass.pocketoa.model.impl.ProductModelImpl;
import top.kass.pocketoa.presenter.ProductPresenter;
import top.kass.pocketoa.view.ProductView;

public class ProductPresenterImpl implements ProductPresenter, ProductModel.OnLoadProductsListener {

    private ProductView mProductView;
    private ProductModel mProductModel;

    public ProductPresenterImpl(ProductView productView) {
        this.mProductView = productView;
        mProductModel = new ProductModelImpl();
    }

    @Override
    public void loadProducts(final int pageIndex) {
        if(pageIndex == 0) {
            mProductView.showProgress();
        }
        mProductModel.loadProducts(pageIndex, this);
    }

    @Override
    public void loadProducts(int opportunityId, int page) {
        if(page == 0) {
            mProductView.showProgress();
        }
        mProductModel.loadProductsBySource(opportunityId, 2, page, this);
    }

    @Override
    public void onSuccess(List<ProductBean> list) {
        mProductView.hideProgress();
        mProductView.addProducts(list);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mProductView.hideProgress();
        mProductView.showLoadFailMsg();
    }
}
