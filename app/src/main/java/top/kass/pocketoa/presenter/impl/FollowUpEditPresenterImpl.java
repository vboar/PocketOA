package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.FollowUpBean;
import top.kass.pocketoa.model.FollowUpModel;
import top.kass.pocketoa.model.impl.FollowUpModelImpl;
import top.kass.pocketoa.presenter.FollowUpEditPresenter;
import top.kass.pocketoa.view.FollowUpEditView;

public class FollowUpEditPresenterImpl implements FollowUpEditPresenter, FollowUpModel.OnSingleFollowUpListener {

    private FollowUpEditView mFollowUpEditView;
    private FollowUpModel mFollowUpModel;

    public FollowUpEditPresenterImpl(FollowUpEditView followUpEditView) {
        this.mFollowUpEditView = followUpEditView;
        this.mFollowUpModel = new FollowUpModelImpl();
    }

    @Override
    public void editFollowUp(FollowUpBean followUpBean) {
        mFollowUpEditView.showProgress();
        mFollowUpModel.saveFollowUp(followUpBean, this);
    }

    @Override
    public void onSuccess() {
        mFollowUpEditView.hideProgress();
        mFollowUpEditView.navigateToDetail();
    }

    @Override
    public void onFailure(String msg) {
        mFollowUpEditView.hideProgress();
        mFollowUpEditView.showFailMsg(msg);
    }

}
