package top.kass.pocketoa.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import top.kass.pocketoa.R;
import top.kass.pocketoa.adapter.OpportunityAdapter;
import top.kass.pocketoa.bean.OpportunityBean;
import top.kass.pocketoa.presenter.OpportunityPresenter;
import top.kass.pocketoa.presenter.impl.OpportunityPresenterImpl;
import top.kass.pocketoa.ui.activity.OpportunityDetailActivity;
import top.kass.pocketoa.view.OpportunityView;

public class OpportunityListFragment extends Fragment implements OpportunityView,
        SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private OpportunityAdapter mAdapter;
    private List<OpportunityBean> mData;
    private OpportunityPresenter mOpportunityPresenter;

    private int mType = OpportunityFragment.OPPORTUNITY_MY;
    private int pageIndex = 0;

    public static OpportunityListFragment newInstance(int type) {
        Bundle args = new Bundle();
        OpportunityListFragment fragment = new OpportunityListFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOpportunityPresenter = new OpportunityPresenterImpl(this);
        mType = getArguments().getInt("type");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common_list, null);

        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.primary,
                R.color.primary_dark, R.color.primary_light,
                R.color.accent);
        mSwipeRefreshWidget.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new OpportunityAdapter(getActivity().getApplicationContext());
        mAdapter.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        onRefresh();

        return view;
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mAdapter.getItemCount()
                    && mAdapter.isShowFooter()) {
                mOpportunityPresenter.loadOpportunities(mType, pageIndex);
            }
        }
    };

    private OpportunityAdapter.OnItemClickListener mOnItemClickListener = new OpportunityAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            OpportunityBean opportunity = mAdapter.getItem(position);
            Intent intent = new Intent(getActivity(), OpportunityDetailActivity.class);
            intent.putExtra("opportunity", opportunity);
            startActivity(intent);
        }
    };

    @Override
    public void onRefresh() {
        pageIndex = 0;
        if(mData != null) {
            mData.clear();
        }
        mOpportunityPresenter.loadOpportunities(mType, pageIndex);
    }

    @Override
    public void showProgress() {
        mSwipeRefreshWidget.setRefreshing(true);
    }

    @Override
    public void addOpportunities(List<OpportunityBean> opportunityList) {
        mAdapter.isShowFooter(true);
        if(mData == null) {
            mData = new ArrayList<>();
        }
        mData.addAll(opportunityList);
        if(pageIndex == 0) {
            mAdapter.setmData(mData);
        } else {
            if(opportunityList == null || opportunityList.size() == 0) {
                mAdapter.isShowFooter(false);
            }
            mAdapter.notifyDataSetChanged();
        }
        pageIndex += 1;
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshWidget.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg() {
        if(pageIndex == 0) {
            mAdapter.isShowFooter(false);
            mAdapter.notifyDataSetChanged();
        }
        View view = getActivity() == null ? mRecyclerView.getRootView() :
                getActivity().findViewById(R.id.drawer_layout);
        Snackbar.make(view, getString(R.string.fail_loading), Snackbar.LENGTH_SHORT).show();
    }

}
