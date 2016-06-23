package top.kass.pocketoa.model.impl;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.Call;
import top.kass.pocketoa.model.UploadModel;

public class UploadModelImpl implements UploadModel {

    @Override
    public void upload(String path, final OnUploadListener listener) {
        File file = new File(path);
        String url = "http://www.mebox.wiki/Home/Picture/Upload";
        OkHttpUtils
                .post()
                .url(url)
                .addFile("resource", file.getName(), file)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onUploadFailure("上传失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("result") == 1) {
                                listener.onUploadSuccess(jsonObject.getString("url"));
                            } else {
                                listener.onUploadFailure("上传失败");
                            }
                        } catch (JSONException e) {
                            listener.onUploadFailure("上传失败");
                        }

                    }
                });
    }

}
