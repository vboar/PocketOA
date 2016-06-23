package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.ProductBean;
import top.kass.pocketoa.model.ProductModel;
import top.kass.pocketoa.model.UploadModel;
import top.kass.pocketoa.model.impl.ProductModelImpl;
import top.kass.pocketoa.model.impl.UploadModelImpl;
import top.kass.pocketoa.presenter.ProductEditPresenter;
import top.kass.pocketoa.view.ProductEditView;

public class ProductEditPresenterImpl implements ProductEditPresenter, ProductModel.OnSingleProductListener,
        UploadModel.OnUploadListener {

    private ProductEditView mProductEditView;
    private ProductModel mProductModel;
    private UploadModel mUploadModel;

    public ProductEditPresenterImpl(ProductEditView productEditView) {
        this.mProductEditView = productEditView;
        this.mProductModel = new ProductModelImpl();
        this.mUploadModel = new UploadModelImpl();
    }

    @Override
    public void editProduct(ProductBean productBean) {
        mProductEditView.showProgress();
        mProductModel.saveProduct(productBean, this);
    }

    @Override
    public void uploadImage(String path) {
        mProductEditView.showProgress();
        mUploadModel.upload(path, this);
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

    @Override
    public void onUploadSuccess(String url) {
        mProductEditView.hideProgress();
        mProductEditView.showImage(url);
    }

    @Override
    public void onUploadFailure(String msg) {
        mProductEditView.hideProgress();
        mProductEditView.showFailMsg(msg);
    }

}
