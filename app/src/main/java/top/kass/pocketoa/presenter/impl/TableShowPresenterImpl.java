package top.kass.pocketoa.presenter.impl;

import java.util.List;

import top.kass.pocketoa.bean.OpportunityBean;
import top.kass.pocketoa.model.OpportunityModel;
import top.kass.pocketoa.model.impl.OpportunityModelImpl;
import top.kass.pocketoa.presenter.TableShowPresenter;
import top.kass.pocketoa.view.TableShowView;

public class TableShowPresenterImpl implements TableShowPresenter, OpportunityModel.OnLoadOpportunitiesListener {

    private TableShowView mTableShowView;
    private OpportunityModel mOpportunityModel;
    private int year;
    private int month;

    public TableShowPresenterImpl(TableShowView tableShowView) {
        this.mTableShowView = tableShowView;
        this.mOpportunityModel = new OpportunityModelImpl();
    }

    @Override
    public void showTable(int type, int staffId, int year, int month) {
        this.year = year;
        this.month = month;
        mTableShowView.showProgress();
        mOpportunityModel.loadOpportunities(type, staffId, -1, this);
    }

    @Override
    public void onLoadSuccess(List<OpportunityBean> list) {
        int[] data1 = new int[8];
        double[] data2 = new double[8];
        for (int i = 0; i < 8; i++) {
            data1[i] = 0;
            data2[i] = 0;
        }
        for (OpportunityBean item: list) {
            if (item.getAcquisitionDate() == null || item.getAcquisitionDate().equals("")) continue;
            int y = Integer.parseInt(item.getAcquisitionDate().substring(0, 4));
            if (y != year) continue;
            int m = Integer.parseInt(item.getAcquisitionDate().substring(5, 7));
            if (m != month) continue;
            if (item.getOpportunityStatus() == null) continue;
            if (item.getEstimatedAmount() == null) continue;
            if (item.getOpportunityStatus() < 7 && item.getOpportunityStatus() > 0) {
                data1[item.getOpportunityStatus()] +=1;
                data1[7] +=1;
                data2[item.getOpportunityStatus()] += item.getEstimatedAmount();
                data2[7] += item.getEstimatedAmount();
            }
        }
        mTableShowView.hideProgress();
        mTableShowView.showTable(data1, data2);
    }

    @Override
    public void onLoadFailure(String msg) {
        mTableShowView.hideProgress();
        mTableShowView.showLoadFailMsg();
    }
}
