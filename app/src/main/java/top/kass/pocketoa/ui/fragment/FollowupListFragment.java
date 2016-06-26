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
import top.kass.pocketoa.adapter.FollowUpAdapter;
import top.kass.pocketoa.bean.FollowUpBean;
import top.kass.pocketoa.presenter.FollowUpPresenter;
import top.kass.pocketoa.presenter.impl.FollowUpPresenterImpl;
import top.kass.pocketoa.ui.activity.FollowUpDetailActivity;
import top.kass.pocketoa.view.FollowUpView;

public class FollowUpListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, FollowUpView {

    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private FollowUpAdapter mAdapter;
    private List<FollowUpBean> mData;
    private FollowUpPresenter mFollowUpPresenter;

    private int pageIndex = 0;
    private int sourceId;
    private int sourceType;
    private int staffId;

    public static FollowUpListFragment newInstance(int sourceId, int sourceType) {
        FollowUpListFragment fragment = new FollowUpListFragment();
        Bundle args = new Bundle();
        args.putInt("sourceId", sourceId);
        args.putInt("sourceType", sourceType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFollowUpPresenter = new FollowUpPresenterImpl(this);

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
        mAdapter = new FollowUpAdapter(getActivity().getApplicationContext());
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
                mFollowUpPresenter.loadFollowUps(sourceId, sourceType, pageIndex);
            }
        }
    };

    private FollowUpAdapter.OnItemClickListener mOnItemClickListener = new FollowUpAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            FollowUpBean followUp = mAdapter.getItem(position);
            Intent intent = new Intent(getActivity(), FollowUpDetailActivity.class);
            intent.putExtra("followUp", followUp);
            startActivityForResult(intent, 1);
        }
    };

    @Override
    public void onRefresh() {
        pageIndex = 0;
        if(mData != null) {
            mData.clear();
        }
        mFollowUpPresenter.loadFollowUps(sourceId, sourceType, pageIndex);
    }


    @Override
    public void showProgress() {
        mSwipeRefreshWidget.setRefreshing(true);
    }

    @Override
    public void addFollowUps(List<FollowUpBean> followUpList) {
        if(mData == null) {
            mData = new ArrayList<>();
        }
        mData.addAll(followUpList);
        if(pageIndex == 0) {
            if (mData.size() < 10) {
                mAdapter.isShowFooter(false);
            }
            mAdapter.setmData(mData);
        } else {
            if(followUpList == null || followUpList.size() == 0) {
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
