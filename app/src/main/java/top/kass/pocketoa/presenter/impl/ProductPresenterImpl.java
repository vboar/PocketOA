package top.kass.pocketoa.presenter.impl;

import java.util.ArrayList;
import java.util.List;

import top.kass.pocketoa.bean.ContactBean;
import top.kass.pocketoa.bean.ProductBean;
import top.kass.pocketoa.presenter.ContactPresenter;
import top.kass.pocketoa.presenter.ProductPresenter;
import top.kass.pocketoa.view.ContactView;
import top.kass.pocketoa.view.ProductView;

public class ProductPresenterImpl implements ProductPresenter {

    private ProductView mProductView;

    public ProductPresenterImpl(ProductView productView) {
        this.mProductView = productView;
    }

    @Override
    public void loadProducts(final int pageIndex) {
        if(pageIndex == 0) {
            mProductView.showProgress();
        }
        mProductView.hideProgress();
        // TODO
        List<ProductBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProductBean productBean = new ProductBean();
            productBean.setName("扫描仪");
            productBean.setSn(458000124);
            list.add(productBean);
        }
        if (pageIndex > 5) {
            mProductView.addProducts(new ArrayList<ProductBean>());
        } else {
            mProductView.addProducts(list);
        }
    }


}
