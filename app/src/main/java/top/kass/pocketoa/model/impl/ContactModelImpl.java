package top.kass.pocketoa.model.impl;

import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import top.kass.pocketoa.bean.ContactBean;
import top.kass.pocketoa.bean.CustomerBean;
import top.kass.pocketoa.model.ContactModel;
import top.kass.pocketoa.util.ToolsUtil;
import top.kass.pocketoa.util.UrlUtil;

public class ContactModelImpl implements ContactModel {

    @Override
    public void loadContacts(int type, int staffId, int page, final OnLoadContactsListener listener) {
        String url = UrlUtil.URL_PREFIX + "common_contacts_json";
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
                                List<ContactBean> list = new ArrayList<>();
                                int count = jsonObject.getInt("currentcount");
                                for (int i = 0; i < count; i++) {
                                    list.add(jsonToContactBean(
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
                                List<ContactBean> list = new ArrayList<>();
                                int count = jsonObject.getInt("currentcount");
                                for (int i = 0; i < count; i++) {
                                    list.add(jsonToContactBean(
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
    public void loadContact(int contactId, final OnLoadContactListner listener) {
        String url = UrlUtil.URL_PREFIX + "contact_query_json";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("contactid", Integer.toString(contactId))
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
                            listener.onLoadSuccess(jsonToSimpleContactBean(jsonObject.getJSONObject("0")));
                        } catch (JSONException e) {
                            listener.onLoadFailure("加载失败");
                        }
                    }
                });
    }

    @Override
    public void addContact(ContactBean contactBean, final OnSingleContactListener listener) {
        String url = UrlUtil.URL_PREFIX + "contact_create_json";
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
    public void saveContact(ContactBean contactBean, final OnSingleContactListener listener) {
        String url = UrlUtil.URL_PREFIX + "contact_modify_json";
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
    public void deleteContact(int contactId, final OnSingleContactListener listener) {
        String url = UrlUtil.URL_PREFIX + "contact_delete_json";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("contactsId", Integer.toString(contactId))
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

    private ContactBean jsonToContactBean(JSONObject object) throws JSONException {
        ContactBean contactBean = new ContactBean();
        contactBean.setContactsId(ToolsUtil.sti(object.getString("contactsid")));
        contactBean.setCustomerId(ToolsUtil.sti(object.getString("customerid")));
        contactBean.setContactsName(ToolsUtil.sts(object.getString("contactsname")));
        contactBean.setContactsAge(ToolsUtil.sti(object.getString("contactsage")));
        contactBean.setContactsGender(ToolsUtil.sts(object.getString("contactsgender")));
        contactBean.setContactsMobile(ToolsUtil.sts(object.getString("contactsmobile")));
        contactBean.setContactsTelephone(ToolsUtil.sts(object.getString("contactstelephone")));
        contactBean.setContactsEmail(ToolsUtil.sts(object.getString("contactsemail")));
        contactBean.setContactsAddress(ToolsUtil.sts(object.getString("contactsaddress")));
        contactBean.setContactsZipcode(ToolsUtil.sts(object.getString("contactszipcode")));
        contactBean.setContactsQq(ToolsUtil.sts(object.getString("contactsqq")));
        contactBean.setContactsWechat(ToolsUtil.sts(object.getString("contactswechat")));
        contactBean.setContactsWangwang(ToolsUtil.sts(object.getString("contactswangwang")));
        contactBean.setContactsDeptName(ToolsUtil.sts(object.getString("contactsdeptname")));
        contactBean.setContactsPosition(ToolsUtil.sti(object.getString("contactsposition")));
        contactBean.setContactsRemarks(ToolsUtil.sts(object.getString("contactsremarks")));
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
        contactBean.setCustomer(customerBean);
        return contactBean;
    }

    private ContactBean jsonToSimpleContactBean(JSONObject object) throws JSONException {
        ContactBean contactBean = new ContactBean();
        contactBean.setContactsId(ToolsUtil.sti(object.getString("contactsid")));
        contactBean.setCustomerId(ToolsUtil.sti(object.getString("customerid")));
        contactBean.setContactsName(ToolsUtil.sts(object.getString("contactsname")));
        contactBean.setContactsAge(ToolsUtil.sti(object.getString("contactsage")));
        contactBean.setContactsGender(ToolsUtil.sts(object.getString("contactsgender")));
        contactBean.setContactsMobile(ToolsUtil.sts(object.getString("contactsmobile")));
        contactBean.setContactsTelephone(ToolsUtil.sts(object.getString("contactstelephone")));
        contactBean.setContactsEmail(ToolsUtil.sts(object.getString("contactsemail")));
        contactBean.setContactsAddress(ToolsUtil.sts(object.getString("contactsaddress")));
        contactBean.setContactsZipcode(ToolsUtil.sts(object.getString("contactszipcode")));
        contactBean.setContactsQq(ToolsUtil.sts(object.getString("contactsqq")));
        contactBean.setContactsWechat(ToolsUtil.sts(object.getString("contactswechat")));
        contactBean.setContactsWangwang(ToolsUtil.sts(object.getString("contactswangwang")));
        contactBean.setContactsDeptName(ToolsUtil.sts(object.getString("contactsdeptname")));
        contactBean.setContactsPosition(ToolsUtil.sti(object.getString("contactsposition")));
        contactBean.setContactsRemarks(ToolsUtil.sts(object.getString("contactsremarks")));
        return contactBean;
    }

}
