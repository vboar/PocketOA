package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.ProductBean;
import top.kass.pocketoa.model.ProductModel;
import top.kass.pocketoa.model.UploadModel;
import top.kass.pocketoa.model.impl.ProductModelImpl;
import top.kass.pocketoa.model.impl.UploadModelImpl;
import top.kass.pocketoa.presenter.ProductAddPresenter;
import top.kass.pocketoa.view.ProductAddView;

public class ProductAddPresenterImpl implements ProductAddPresenter, ProductModel.OnSingleProductListener,
        UploadModel.OnUploadListener {

    private ProductAddView mProductAddView;
    private ProductModel mProductModel;
    private UploadModel mUploadModel;

    public ProductAddPresenterImpl(ProductAddView productAddView) {
        this.mProductAddView = productAddView;
        this.mProductModel = new ProductModelImpl();
        this.mUploadModel = new UploadModelImpl();
    }

    @Override
    public void addProduct(ProductBean productBean) {
        mProductAddView.showProgress();
        mProductModel.addProduct(productBean, this);
    }

    @Override
    public void uploadImage(String path) {
        mProductAddView.showProgress();
        mUploadModel.upload(path, this);
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

    @Override
    public void onUploadSuccess(String url) {
        mProductAddView.hideProgress();
        mProductAddView.showImage(url);
    }

    @Override
    public void onUploadFailure(String msg) {
        mProductAddView.hideProgress();
        mProductAddView.showFailMsg(msg);
    }
}
