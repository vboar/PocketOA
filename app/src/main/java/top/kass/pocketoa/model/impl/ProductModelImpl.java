package top.kass.pocketoa.model.impl;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
                .addParams("currentpage", Integer.toString(page+1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFailure("加载失败", e);
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
                            listener.onFailure("加载失败", e);
                        }
                    }
                });
    }

    @Override
    public void loadProduct(int productId, final OnLoadProductListner listener) {
        String url = UrlUtil.URL_PREFIX + "product_query_json";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("productid", Integer.toString(productId))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onLoadFailure("加载失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            listener.onLoadSuccess(jsonToProductBean(jsonObject.getJSONObject("0")));
                        } catch (JSONException e) {
                            listener.onLoadFailure("加载失败");
                        }
                    }
                });
    }

    @Override
    public void addProduct(ProductBean productBean, final OnSingleProductListener listener) {
        String url = UrlUtil.URL_PREFIX + "product_create_json";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("productname", productBean.getProductName())
                .addParams("productsn", productBean.getProductSn())
                .addParams("standardprice", productBean.getStandardPrice().toString())
                .addParams("salesunit", productBean.getSalesUnit())
                .addParams("unitcost", productBean.getUnitCost().toString())
                .addParams("introduction", productBean.getIntroduction())
                .addParams("productremarks", productBean.getProductRemarks())
                .addParams("picture", productBean.getPicture())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFailure("添加失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("resultcode") == 0) {
                                listener.onSuccess();
                            } else {
                                listener.onFailure("添加失败");
                            }
                        } catch (JSONException e) {
                            listener.onFailure("添加失败");
                        }

                    }
                });
    }

    @Override
    public void saveProduct(ProductBean productBean, final OnSingleProductListener listener) {
        String url = UrlUtil.URL_PREFIX + "product_modify_json";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("productid", productBean.getProductId().toString())
                .addParams("productname", productBean.getProductName())
                .addParams("productsn", productBean.getProductSn())
                .addParams("standardprice", productBean.getStandardPrice().toString())
                .addParams("salesunit", productBean.getSalesUnit())
                .addParams("unitcost", productBean.getUnitCost().toString())
                .addParams("introduction", productBean.getIntroduction())
                .addParams("productremarks", productBean.getProductRemarks())
                .addParams("picture", productBean.getPicture())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFailure("编辑失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("resultcode") == 0) {
                                listener.onSuccess();
                            } else {
                                listener.onFailure("编辑失败");
                            }
                        } catch (JSONException e) {
                            listener.onFailure("编辑失败");
                        }

                    }
                });
    }

    @Override
    public void deleteProduct(int productId, final OnSingleProductListener listener) {
        String url = UrlUtil.URL_PREFIX + "product_delete_json";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("productid", Integer.toString(productId))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFailure("删除失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("resultcode") == 0) {
                                listener.onSuccess();
                            } else {
                                listener.onFailure("删除失败");
                            }
                        } catch (JSONException e) {
                            listener.onFailure("删除失败");
                        }

                    }
                });
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