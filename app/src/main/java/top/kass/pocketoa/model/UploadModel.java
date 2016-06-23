package top.kass.pocketoa.model;

public interface UploadModel {

    void upload(String path, OnUploadListener listener);

    interface OnUploadListener {
        void onUploadSuccess(String url);
        void onUploadFailure(String msg);
    }

}
