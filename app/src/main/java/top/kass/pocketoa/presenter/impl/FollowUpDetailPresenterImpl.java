package top.kass.pocketoa.presenter.impl;

import top.kass.pocketoa.bean.FollowUpBean;
import top.kass.pocketoa.model.FollowUpModel;
import top.kass.pocketoa.model.impl.FollowUpModelImpl;
import top.kass.pocketoa.presenter.FollowUpDetailPresenter;
import top.kass.pocketoa.view.FollowUpDetailView;

public class FollowUpDetailPresenterImpl implements FollowUpDetailPresenter,
        FollowUpModel.OnLoadFollowUpListener,
        FollowUpModel.OnSingleFollowUpListener {

    private FollowUpDetailView mFollowUpDetailView;
    private FollowUpModel mFollowUpModel;

    public FollowUpDetailPresenterImpl(FollowUpDetailView followUpDetailView) {
        this.mFollowUpDetailView = followUpDetailView;
        this.mFollowUpModel = new FollowUpModelImpl();
    }

    @Override
    public void loadFollowUp(int followUpId) {
        mFollowUpDetailView.showProgress("正在加载...");
        mFollowUpModel.loadFollowUp(followUpId, this);
    }

    @Override
    public void deleteFollowUp(int followUpId) {
        mFollowUpDetailView.showProgress("正在删除...");
        mFollowUpModel.deleteFollowUp(followUpId, this);
    }

    @Override
    public void onLoadSuccess(FollowUpBean followUpBean) {
        mFollowUpDetailView.hideProgress();
        mFollowUpDetailView.loadFollowUp(followUpBean);
    }

    @Override
    public void onLoadFailure(String msg) {
        mFollowUpDetailView.hideProgress();
        mFollowUpDetailView.showFailMsg(msg);
    }

    @Override
    public void onSuccess() {
        mFollowUpDetailView.hideProgress();
        mFollowUpDetailView.navigateToList(2);
    }

    @Override
    public void onFailure(String msg) {
        mFollowUpDetailView.hideProgress();
        mFollowUpDetailView.showFailMsg(msg);
    }

}
