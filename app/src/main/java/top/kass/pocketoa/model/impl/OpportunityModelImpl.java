package top.kass.pocketoa.model.impl;

import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.utils.Exceptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import top.kass.pocketoa.bean.CustomerBean;
import top.kass.pocketoa.bean.OpportunityBean;
import top.kass.pocketoa.model.OpportunityModel;
import top.kass.pocketoa.util.ToolsUtil;
import top.kass.pocketoa.util.UrlUtil;

public class OpportunityModelImpl implements OpportunityModel {

    @Override
    public void loadOpportunities(int type, int staffId, int page, final OnLoadOpportunitiesListener listener) {
        String url = UrlUtil.URL_PREFIX + "common_opportunity_json";
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
                                List<OpportunityBean> list = new ArrayList<>();
                                int count = jsonObject.getInt("currentcount");
                                for (int i = 0; i < count; i++) {
                                    list.add(jsonToOpportunityBean(
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
                                List<OpportunityBean> list = new ArrayList<>();
                                int count = jsonObject.getInt("currentcount");
                                for (int i = 0; i < count; i++) {
                                    list.add(jsonToOpportunityBean(
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
    public void loadOpportunity(int opportunityId, final OnLoadOpportunityListener listener) {
        String url = UrlUtil.URL_PREFIX + "opportunity_query_json";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("opportunityid", Integer.toString(opportunityId))
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
                            listener.onLoadSuccess(jsonToOpportunityBean(jsonObject.getJSONObject("0")));
                        } catch (JSONException e) {
                            listener.onLoadFailure("加载失败");
                        }
                    }
                });
    }

    @Override
    public void addOpportunity(OpportunityBean opportunityBean, final OnSingleOpportunityListener listener) {
        String url = UrlUtil.URL_PREFIX + "opportunity_create_json";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("opportunitytitle", opportunityBean.getOpportunityTitle())
                .addParams("customerid", Integer.toString(opportunityBean.getCustomerId()))
                .addParams("estimatedamount", Double.toString(opportunityBean.getEstimatedAmount()))
                .addParams("expecteddate", opportunityBean.getExpectedDate())
                .addParams("opportunitystatus", Integer.toString(opportunityBean.getOpportunityStatus()))
                .addParams("channel", opportunityBean.getChannel())
                .addParams("businesstype", Integer.toString(opportunityBean.getBusinessType()))
                .addParams("acquisitiondate", opportunityBean.getAcquisitionDate())
                .addParams("opportunitiessource", opportunityBean.getOpportunitiesSource())
                .addParams("staffid", Integer.toString(opportunityBean.getStaffId()))
                .addParams("opportunityremarks", opportunityBean.getOpportunityRemarks())
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
    public void saveOpportunity(OpportunityBean opportunityBean, final OnSingleOpportunityListener listener) {
        String url = UrlUtil.URL_PREFIX + "opportunity_modify_json";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("opportunityid", Integer.toString(opportunityBean.getOpportunityId()))
                .addParams("opportunitytitle", opportunityBean.getOpportunityTitle())
                .addParams("customerid", Integer.toString(opportunityBean.getCustomerId()))
                .addParams("estimatedamount", Double.toString(opportunityBean.getEstimatedAmount()))
                .addParams("expecteddate", opportunityBean.getExpectedDate())
                .addParams("opportunitystatus", Integer.toString(opportunityBean.getOpportunityStatus()))
                .addParams("channel", opportunityBean.getChannel())
                .addParams("businesstype", Integer.toString(opportunityBean.getBusinessType()))
                .addParams("acquisitiondate", opportunityBean.getAcquisitionDate())
                .addParams("opportunitiessource", opportunityBean.getOpportunitiesSource())
                .addParams("staffid", Integer.toString(opportunityBean.getStaffId()))
                .addParams("opportunityremarks", opportunityBean.getOpportunityRemarks())
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
    public void deleteOpportunity(int opportunityId, final OnSingleOpportunityListener listener) {
        String url = UrlUtil.URL_PREFIX + "opportunity_delete_json";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("opportunityid", Integer.toString(opportunityId))
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

    private OpportunityBean jsonToOpportunityBean(JSONObject object) throws JSONException {
        OpportunityBean opportunityBean = new OpportunityBean();
        opportunityBean.setOpportunityId(ToolsUtil.sti(object.getString("opportunityid")));
        opportunityBean.setOpportunityTitle(ToolsUtil.sts(object.getString("opportunitytitle")));
        opportunityBean.setCustomerId(ToolsUtil.sti(object.getString("customerid")));
        opportunityBean.setEstimatedAmount(ToolsUtil.std(object.getString("estimatedamount")));
        opportunityBean.setSuccessRate(ToolsUtil.sti(object.getString("successrate")));
        opportunityBean.setExpectedDate(ToolsUtil.sts(object.getString("expecteddate")));
        opportunityBean.setOpportunityStatus(ToolsUtil.sti(object.getString("opportunitystatus")));
        opportunityBean.setChannel(ToolsUtil.sts(object.getString("channel")));
        opportunityBean.setBusinessType(ToolsUtil.sti(object.getString("businesstype")));
        opportunityBean.setAcquisitionDate(ToolsUtil.sts(object.getString("acquisitiondate")));
        opportunityBean.setOpportunitiesSource(ToolsUtil.sts(object.getString("opportunitiessource")));
        opportunityBean.setStaffId(ToolsUtil.sti(object.getString("staffid")));
        opportunityBean.setOpportunityRemarks(ToolsUtil.sts(object.getString("opportunityremarks")));
        try {
            CustomerBean customerBean = new CustomerBean();
            customerBean.setCustomerId(ToolsUtil.sti(object.getString("customerid")));
            customerBean.setCustomerName(ToolsUtil.sts(object.getString("customername")));
            opportunityBean.setCustomer(customerBean);
        } catch (Exception e) {

        }
        return opportunityBean;
    }

}
