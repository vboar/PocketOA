package top.kass.pocketoa.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import top.kass.pocketoa.adapter.ContactAdapter;
import top.kass.pocketoa.adapter.ContractAdapter;
import top.kass.pocketoa.bean.ContactBean;
import top.kass.pocketoa.bean.ContractBean;
import top.kass.pocketoa.presenter.ContractPresenter;
import top.kass.pocketoa.presenter.impl.ContractPresenterImpl;
import top.kass.pocketoa.ui.activity.ContactDetailActivity;
import top.kass.pocketoa.ui.activity.ContractDetailActivity;
import top.kass.pocketoa.view.ContractView;

public class ContractListFragment extends Fragment implements ContractView,
        SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ContractAdapter mAdapter;
    private List<ContractBean> mData;
    private ContractPresenter mContractPresenter;

    private int mType = ContractFragment.CONTRACT_MY;
    private int pageIndex = 0;
    private int staffId;
    private int sourceId;
    private int sourceType;

    public static ContractListFragment newInstance(int type, int sourceId, int sourceType) {
        Bundle args = new Bundle();
        ContractListFragment fragment = new ContractListFragment();
        args.putInt("type", type);
        args.putInt("sourceId", sourceId);
        args.putInt("sourceType", sourceType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContractPresenter = new ContractPresenterImpl(this);
        mType = getArguments().getInt("type");
        sourceId = getArguments().getInt("sourceId");
        sourceType = getArguments().getInt("sourceType");

        SharedPreferences sharedPreferences = getActivity().
                getSharedPreferences("oa", Context.MODE_PRIVATE);
        staffId = sharedPreferences.getInt("staffId", 0);
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
        mAdapter = new ContractAdapter(getActivity().getApplicationContext());
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
                if (sourceId == -1) {
                    mContractPresenter.loadContracts(mType, staffId, pageIndex);
                } else {
                    mContractPresenter.loadContractsBySource(sourceId, sourceType, pageIndex);
                }
            }
        }
    };

    private ContractAdapter.OnItemClickListener mOnItemClickListener = new ContractAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            ContractBean contract = mAdapter.getItem(position);
            Intent intent = new Intent(getActivity(), ContractDetailActivity.class);
            intent.putExtra("contract", contract);
            startActivityForResult(intent, 1);
        }
    };

    @Override
    public void onRefresh() {
        pageIndex = 0;
        if(mData != null) {
            mData.clear();
        }
        if (sourceId == -1) {
            mContractPresenter.loadContracts(mType, staffId, pageIndex);
        } else {
            mContractPresenter.loadContractsBySource(sourceId, sourceType, pageIndex);
        }
    }

    @Override
    public void showProgress() {
        mSwipeRefreshWidget.setRefreshing(true);
    }

    @Override
    public void addContracts(List<ContractBean> contractList) {
        mAdapter.isShowFooter(true);
        if(mData == null) {
            mData = new ArrayList<>();
        }
        mData.addAll(contractList);
        if(pageIndex == 0) {
            if (mData.size() < 10) {
                mAdapter.isShowFooter(false);
            }
            mAdapter.setmData(mData);
        } else {
            if(contractList == null || contractList.size() == 0) {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 1) {
            onRefresh();
        }
    }

}
