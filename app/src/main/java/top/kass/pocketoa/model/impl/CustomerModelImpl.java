package top.kass.pocketoa.model.impl;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import top.kass.pocketoa.bean.CustomerBean;
import top.kass.pocketoa.model.CustomerModel;
import top.kass.pocketoa.util.ToolsUtil;
import top.kass.pocketoa.util.UrlUtil;

public class CustomerModelImpl implements CustomerModel {

    @Override
    public void loadCustomers(int type, int staffId, int page, final OnLoadCustomersListener listener) {
        String url = UrlUtil.URL_PREFIX + "common_customer_json";
        if (type == 0) {
            OkHttpUtils
                    .post()
                    .url(url)
                    .addParams("currentpage", Integer.toString(page+1))
                    .addParams("staffid", Integer.toString(staffId))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            listener.onFailure("加载失败");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                List<CustomerBean> list = new ArrayList<>();
                                int count = jsonObject.getInt("currentcount");
                                for (int i = 0; i < count; i++) {
                                    list.add(jsonToCustomerBean(
                                            jsonObject.getJSONObject(Integer.toString(i))));
                                }
                                listener.onSuccess(list);
                            } catch (JSONException e) {
                                listener.onFailure("加载失败");
                            }
                        }
                    });
        } else {
            OkHttpUtils
                    .post()
                    .url(url)
                    .addParams("currentpage", Integer.toString(page+1))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            listener.onFailure("加载失败");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                List<CustomerBean> list = new ArrayList<>();
                                int count = jsonObject.getInt("currentcount");
                                for (int i = 0; i < count; i++) {
                                    list.add(jsonToCustomerBean(
                                            jsonObject.getJSONObject(Integer.toString(i))));
                                }
                                listener.onSuccess(list);
                            } catch (JSONException e) {
                                listener.onFailure("加载失败");
                            }
                        }
                    });
        }
    }

    @Override
    public void loadCustomer(int customerId, final OnLoadCustomerListener listener) {
        String url = UrlUtil.URL_PREFIX + "customer_query_json";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("customerid", Integer.toString(customerId))
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
                            listener.onLoadSuccess(jsonToCustomerBean(jsonObject.getJSONObject("0")));
                        } catch (JSONException e) {
                            listener.onLoadFailure("加载失败");
                        }
                    }
                });
    }

    @Override
    public void addCustomer(CustomerBean customerBean, final OnSingleCustomerListener listener) {
        String url = UrlUtil.URL_PREFIX + "customer_create_json";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("customername", customerBean.getCustomerName())
                .addParams("profile", customerBean.getProfile())
                .addParams("customertype", Integer.toString(customerBean.getCustomerType()))
                .addParams("customerstatus", Integer.toString(customerBean.getCustomerStatus()))
                .addParams("customersource", customerBean.getCustomerSource())
                .addParams("size", Integer.toString(customerBean.getSize()))
                .addParams("telephone", customerBean.getTelephone())
                .addParams("email", customerBean.getEmail())
                .addParams("website", customerBean.getWebsite())
                .addParams("address", customerBean.getAddress())
                .addParams("zipcode", customerBean.getZipcode())
                .addParams("staffid", Integer.toString(customerBean.getStaffId()))
                .addParams("customerremarks", customerBean.getCustomerRemarks())
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
    public void saveCustomer(CustomerBean customerBean, final OnSingleCustomerListener listener) {
        String url = UrlUtil.URL_PREFIX + "customer_modify_json";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("customername", customerBean.getCustomerName())
                .addParams("customerid", Integer.toString(customerBean.getCustomerId()))
                .addParams("profile", customerBean.getProfile())
                .addParams("customertype", Integer.toString(customerBean.getCustomerType()))
                .addParams("customerstatus", Integer.toString(customerBean.getCustomerStatus()))
                .addParams("customersource", customerBean.getCustomerSource())
                .addParams("size", Integer.toString(customerBean.getSize()))
                .addParams("telephone", customerBean.getTelephone())
                .addParams("email", customerBean.getEmail())
                .addParams("website", customerBean.getWebsite())
                .addParams("address", customerBean.getAddress())
                .addParams("zipcode", customerBean.getZipcode())
                .addParams("staffid", Integer.toString(customerBean.getStaffId()))
                .addParams("customerremarks", customerBean.getCustomerRemarks())
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
                            listener.onSuccess();
                        } catch (JSONException e) {
                            listener.onFailure("编辑失败");
                        }
                    }
                });
    }

    @Override
    public void deleteCustomer(int customerId, final OnSingleCustomerListener listener) {
        String url = UrlUtil.URL_PREFIX + "customer_delete_json";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("customerid", Integer.toString(customerId))
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

    private CustomerBean jsonToCustomerBean(JSONObject object) throws JSONException {
        CustomerBean customerBean = new CustomerBean();
        customerBean.setCustomerId(ToolsUtil.sti(object.getString("customerid")));
        customerBean.setCustomerName(ToolsUtil.sts(object.getString("customername")));
        customerBean.setProfile(ToolsUtil.sts(object.getString("profile")));
        customerBean.setCustomerType(ToolsUtil.sti(object.getString("customertype")));
        customerBean.setCustomerStatus(ToolsUtil.sti(object.getString("customerstatus")));
        customerBean.setRegionId(ToolsUtil.sti(object.getString("regionid")));
        customerBean.setParentCustomerId(ToolsUtil.sti(object.getString("parentcustomerid")));
        customerBean.setCustomerSource(ToolsUtil.sts(object.getString("customersource")));
        customerBean.setSize(ToolsUtil.sti(object.getString("size")));
        customerBean.setTelephone(ToolsUtil.sts(object.getString("telephone")));
        customerBean.setEmail(ToolsUtil.sts(object.getString("email")));
        customerBean.setWebsite(ToolsUtil.sts(object.getString("website")));
        customerBean.setAddress(ToolsUtil.sts(object.getString("address")));
        customerBean.setZipcode(ToolsUtil.sts(object.getString("zipcode")));
        customerBean.setStaffId(ToolsUtil.sti(object.getString("staffid")));
        customerBean.setCreateDate(ToolsUtil.sts(object.getString("createdate")));
        customerBean.setCustomerRemarks(ToolsUtil.sts(object.getString("customerremarks")));
        // TODO
        return customerBean;
    }
}
