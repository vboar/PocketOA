package top.kass.pocketoa.model.impl;

import android.util.Log;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import top.kass.pocketoa.bean.ProductBean;
import top.kass.pocketoa.model.ProductModel;
import top.kass.pocketoa.util.ToolsUtil;
import top.kass.pocketoa.util.UrlUtil;

public class ProductModelImpl implements ProductModel {

    @Override
    public void loadProducts(int page, final OnLoadProductsListener listener) {
        String url = UrlUtil.URL_PREFIX + "common_product_json";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("currentpage", Integer.toString(page))
                .addParams("search", Integer.toString(page))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFailure("Load products into failure.", e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            List<ProductBean> list = new ArrayList<>();
                            int count = jsonObject.getInt("currentcount");
                            for (int i = 0; i < count; i++) {
                                list.add(jsonToProductBean(
                                        jsonObject.getJSONObject(Integer.toString(i))));
                            }
                            listener.onSuccess(list);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public interface OnLoadProductsListener {
        void onSuccess(List<ProductBean> list);
        void onFailure(String msg, Exception e);
    }

    private ProductBean jsonToProductBean(JSONObject object) throws JSONException {
        ProductBean productBean = new ProductBean();
        productBean.setProductId(ToolsUtil.sti(object.getString("productid")));
        productBean.setProductName(ToolsUtil.sts(object.getString("productname")));
        productBean.setProductSn(ToolsUtil.sts(object.getString("productsn")));
        productBean.setStandardPrice(ToolsUtil.std(object.getString("standardprice")));
        productBean.setSalesUnit(ToolsUtil.sts(object.getString("salesunit")));
        productBean.setUnitCost(ToolsUtil.std(object.getString("unitcost")));
        productBean.setClassification(ToolsUtil.sts(object.getString("classification")));
        productBean.setPicture(ToolsUtil.sts(object.getString("picture")));
        productBean.setIntroduction(ToolsUtil.sts(object.getString("introduction")));
        productBean.setProductRemarks(ToolsUtil.sts(object.getString("productremarks")));
        return productBean;
    }

}