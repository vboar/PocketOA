package top.kass.pocketoa.presenter.impl;

import java.util.List;

import top.kass.pocketoa.bean.FollowUpBean;
import top.kass.pocketoa.model.FollowUpModel;
import top.kass.pocketoa.model.impl.FollowUpModelImpl;
import top.kass.pocketoa.presenter.FollowUpPresenter;
import top.kass.pocketoa.view.FollowUpView;

public class FollowUpPresenterImpl implements FollowUpPresenter, FollowUpModel.OnLoadFollowUpsListener {

    private FollowUpView mFollowUpView;
    private FollowUpModel mFollowUpModel;

    public FollowUpPresenterImpl(FollowUpView followUpView) {
        this.mFollowUpView = followUpView;
        this.mFollowUpModel = new FollowUpModelImpl();
    }

    @Override
    public void loadFollowUps(int sourceId, int sourceType, int page) {
        if (page == 0) {
            mFollowUpView.showProgress();
        }
        mFollowUpModel.loadFollowUps(sourceType, sourceId, page, this);
    }

    @Override
    public void onSuccess(List<FollowUpBean> list) {
        mFollowUpView.hideProgress();
        mFollowUpView.addFollowUps(list);
    }

    @Override
    public void onFailure(String msg) {
        mFollowUpView.hideProgress();
        mFollowUpView.showLoadFailMsg();
    }

}
