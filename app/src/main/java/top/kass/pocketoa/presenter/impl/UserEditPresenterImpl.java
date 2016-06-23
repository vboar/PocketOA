package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.StaffBean;
import top.kass.pocketoa.model.StaffModel;
import top.kass.pocketoa.model.UploadModel;
import top.kass.pocketoa.model.impl.StaffModelImpl;
import top.kass.pocketoa.model.impl.UploadModelImpl;
import top.kass.pocketoa.presenter.UserEditPresenter;
import top.kass.pocketoa.view.UserEditView;

public class UserEditPresenterImpl implements UserEditPresenter, StaffModel.OnLoginFinishedListener,
    StaffModel.OnSaveListener, UploadModel.OnUploadListener {

    private UserEditView mUserEditView;
    private StaffModel mStaffModel;
    private UploadModel mUploadModel;

    public UserEditPresenterImpl(UserEditView userEditView) {
        this.mUserEditView = userEditView;
        this.mStaffModel = new StaffModelImpl();
        this.mUploadModel = new UploadModelImpl();
    }

    @Override
    public void loadStaff(int userId) {
        mUserEditView.showProgress("正在加载...");
        mStaffModel.login(Integer.toString(userId), this);
    }

    @Override
    public void editStaff(StaffBean staffBean) {
        mUserEditView.showProgress("正在提交...");
        mStaffModel.save(staffBean, this);
    }

    @Override
    public void uploadImage(String path) {
        mUserEditView.showProgress("正在上传图片...");
        mUploadModel.upload(path, this);
    }

    @Override
    public void onSuccess(StaffBean staffBean) {
        mUserEditView.hideProgress();
        mUserEditView.setStaffBean(staffBean);
    }

    @Override
    public void onFailure(String msg) {
        mUserEditView.hideProgress();
        mUserEditView.showFailMsg(msg);
    }

    @Override
    public void onSaveSuccess() {
        mUserEditView.hideProgress();
        mUserEditView.navigateToMain();
    }

    @Override
    public void onSaveFailure() {
        mUserEditView.hideProgress();
        mUserEditView.showFailMsg("编辑失败");
    }

    @Override
    public void onUploadSuccess(String url) {
        mUserEditView.hideProgress();
        mUserEditView.showImage(url);
    }

    @Override
    public void onUploadFailure(String msg) {
        mUserEditView.showFailMsg("上传失败");
    }
}
