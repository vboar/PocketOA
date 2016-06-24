package top.kass.pocketoa.model.impl;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import top.kass.pocketoa.bean.ContractBean;
import top.kass.pocketoa.model.ContractModel;
import top.kass.pocketoa.util.UrlUtil;

public class ContractModelImpl implements ContractModel {

    @Override
    public void loadContracts(int type, int staffId, int page, final OnLoadContractsListener listener) {
        String url = UrlUtil.URL_PREFIX + "common_contract_json";
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
                                List<ContractBean> list = new ArrayList<>();
                                int count = jsonObject.getInt("currentcount");
                                for (int i = 0; i < count; i++) {
                                    list.add(jsonToContractBean(
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
                                List<ContractBean> list = new ArrayList<>();
                                int count = jsonObject.getInt("currentcount");
                                for (int i = 0; i < count; i++) {
                                    list.add(jsonToContractBean(
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
    public void loadContract(int contractId, final OnLoadContractListener listener) {
        String url = UrlUtil.URL_PREFIX + "contract_query_json";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("contractid", Integer.toString(contractId))
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
                            listener.onLoadSuccess(jsonToContractBean(jsonObject.getJSONObject("0")));
                        } catch (JSONException e) {
                            listener.onLoadFailure("加载失败");
                        }
                    }
                });
    }

    @Override
    public void addContract(ContractBean contractBean, final OnSingleContractListener listener) {
        String url = UrlUtil.URL_PREFIX + "contract_create_json";
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
    public void saveContract(ContractBean contractBean, final OnSingleContractListener listener) {
        String url = UrlUtil.URL_PREFIX + "contract_modify_json";
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
    public void deleteContract(int contractId, final OnSingleContractListener listener) {
        String url = UrlUtil.URL_PREFIX + "contract_delete_json";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("contractid", Integer.toString(contractId))
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

    private ContractBean jsonToContractBean(JSONObject object) {
        ContractBean contractBean = new ContractBean();
        // TODO
        return contractBean;
    }

}
