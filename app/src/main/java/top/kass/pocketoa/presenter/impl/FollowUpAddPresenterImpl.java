package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.FollowUpBean;
import top.kass.pocketoa.model.FollowUpModel;
import top.kass.pocketoa.model.impl.FollowUpModelImpl;
import top.kass.pocketoa.presenter.FollowUpAddPresenter;
import top.kass.pocketoa.view.FollowUpAddView;

public class FollowUpAddPresenterImpl implements FollowUpAddPresenter, FollowUpModel.OnSingleFollowUpListener {

    private FollowUpAddView mFollowUpAddView;
    private FollowUpModel mFollowUpModel;

    public FollowUpAddPresenterImpl(FollowUpAddView followUpAddView) {
        this.mFollowUpAddView = followUpAddView;
        this.mFollowUpModel = new FollowUpModelImpl();
    }

    @Override
    public void addFollowUp(FollowUpBean followUpBean) {
        mFollowUpAddView.showProgress();
        mFollowUpModel.addFollowUp(followUpBean, this);
    }

    @Override
    public void onSuccess() {
        mFollowUpAddView.hideProgress();
        mFollowUpAddView.navigateToList();
    }

    @Override
    public void onFailure(String msg) {
        mFollowUpAddView.hideProgress();
        mFollowUpAddView.showFailMsg(msg);
    }

}
