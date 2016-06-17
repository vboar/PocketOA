package top.kass.pocketoa.model.impl;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import top.kass.pocketoa.bean.StaffBean;
import top.kass.pocketoa.model.StaffModel;
import top.kass.pocketoa.util.ToolsUtil;
import top.kass.pocketoa.util.UrlUtil;

public class StaffModelImpl implements StaffModel {

    @Override
    public void login(String userId, final OnLoginFinishedListener listener) {

        if (userId.trim().length() == 0) {
            listener.onFailure("用户ID不能为空");
            return;
        }

        String url = UrlUtil.URL_PREFIX + "staff_query_json";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("staffid", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFailure("登录失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("recordcount") == 0) {
                                listener.onFailure("该用户不存在");
                            } else {
                                listener.onSuccess(jsonToStaffBean(jsonObject.getJSONObject("0")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void save(StaffBean staffBean, final OnSaveListener listener) {
        String url = UrlUtil.URL_PREFIX + "staff_modify_json";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("staffid", staffBean.getStaffId().toString())
                .addParams("userid", staffBean.getUserId())
                .addParams("mobile", staffBean.getMobile())
                .addParams("tel", staffBean.getTel())
                .addParams("email", staffBean.getEmail())
                .addParams("gender", staffBean.getGender())
                .addParams("avatar", staffBean.getAvatar())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onSaveFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("resultcode") == 0) {
                                listener.onSaveSuccess();
                            } else {
                                listener.onSaveFailure();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }

    private StaffBean jsonToStaffBean(JSONObject object) throws JSONException {
        StaffBean staffBean = new StaffBean();
        staffBean.setStaffId(ToolsUtil.sti(object.getString("staffid")));
        staffBean.setUserId(ToolsUtil.sts(object.getString("userid")));
        staffBean.setOpenId(ToolsUtil.sts(object.getString("openid")));
        staffBean.setName(ToolsUtil.sts(object.getString("name")));
        staffBean.setDepartmentId(ToolsUtil.sti(object.getString("departmentid")));
        staffBean.setLeaderFlag(ToolsUtil.sti(object.getString("leaderflag")));
        staffBean.setPosition(ToolsUtil.sts(object.getString("position")));
        staffBean.setOrder(ToolsUtil.sti(object.getString("order")));
        staffBean.setMobile(ToolsUtil.sts(object.getString("mobile")));
        staffBean.setTel(ToolsUtil.sts(object.getString("tel")));
        staffBean.setGender(ToolsUtil.sts(object.getString("gender")));
        staffBean.setEmail(ToolsUtil.sts(object.getString("email")));
        staffBean.setWeixinId(ToolsUtil.sts(object.getString("weixinid")));
        staffBean.setAvatar(ToolsUtil.sts(object.getString("avatar")));
        staffBean.setExtattr(ToolsUtil.sts(object.getString("extattr")));
        staffBean.setStaffStatus(ToolsUtil.sti(object.getString("staffstatus")));
        staffBean.setEnable(ToolsUtil.sti(object.getString("enable")));
        staffBean.setStaffRemarks(ToolsUtil.sts(object.getString("staffremarks")));
        return staffBean;
    }


}
