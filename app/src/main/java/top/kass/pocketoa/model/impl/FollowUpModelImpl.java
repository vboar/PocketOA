package top.kass.pocketoa.model.impl;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import top.kass.pocketoa.bean.CustomerBean;
import top.kass.pocketoa.bean.FollowUpBean;
import top.kass.pocketoa.model.FollowUpModel;
import top.kass.pocketoa.util.UrlUtil;

public class FollowUpModelImpl implements FollowUpModel {

    @Override
    public void loadFollowUps(int type, int sourceId, int page, final OnLoadFollowUpsListener listener) {
        String url = UrlUtil.URL_PREFIX + "common_followup_json";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("sourceid", Integer.toString(sourceId))
                .addParams("sourcetype", Integer.toString(type))
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
                            List<FollowUpBean> list = new ArrayList<>();
                            int count = jsonObject.getInt("currentcount");
                            for (int i = 0; i < count; i++) {
                                list.add(jsonToFollowUpBean(
                                        jsonObject.getJSONObject(Integer.toString(i))));
                            }
                            listener.onSuccess(list);
                        } catch (JSONException e) {
                            listener.onFailure("加载失败");
                        }
                    }
                });
    }

    @Override
    public void loadFollowUp(int followupId, final OnLoadFollowUpListener listener) {
        String url = UrlUtil.URL_PREFIX + "followup_query_json";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("followupid", Integer.toString(followupId))
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
                            listener.onLoadSuccess(jsonToFollowUpBean(jsonObject.getJSONObject("0")));
                        } catch (JSONException e) {
                            listener.onLoadFailure("加载失败");
                        }
                    }
                });
    }

    @Override
    public void addFollowUp(FollowUpBean followUpBean, final OnSingleFollowUpListener listener) {
        String url = UrlUtil.URL_PREFIX + "followup_create_json";
        OkHttpUtils
                .post()
                .url(url)
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
    public void saveFollowUp(FollowUpBean followUpBean, final OnSingleFollowUpListener listener) {
        String url = UrlUtil.URL_PREFIX + "followup_modify_json";
        OkHttpUtils
                .post()
                .url(url)
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
    public void deleteFollowUp(int followUpId, final OnSingleFollowUpListener listener) {
        String url = UrlUtil.URL_PREFIX + "followup_delete_json";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("followupid", Integer.toString(followUpId))
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

    private FollowUpBean jsonToFollowUpBean(JSONObject object) {
        FollowUpBean followUpBean = new FollowUpBean();
        // TODO
        return followUpBean;
    }

}
