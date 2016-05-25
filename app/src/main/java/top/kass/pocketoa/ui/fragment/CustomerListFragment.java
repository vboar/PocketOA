package top.kass.pocketoa.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
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
import top.kass.pocketoa.adapter.CustomerAdapter;
import top.kass.pocketoa.bean.CustomerBean;
import top.kass.pocketoa.presenter.CustomerPresenter;
import top.kass.pocketoa.presenter.impl.CustomerPresenterImpl;
import top.kass.pocketoa.ui.activity.CustomerDetailActivity;
import top.kass.pocketoa.view.CustomerView;

public class CustomerListFragment extends Fragment implements CustomerView,
        SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CustomerAdapter mAdapter;
    private List<CustomerBean> mData;
    private CustomerPresenter mCustomerPresenter;

    private int mType = CustomerFragment.CUSTOMER_MY;
    private int pageIndex = 0;

    public static CustomerListFragment newInstance(int type) {
        Bundle args = new Bundle();
        CustomerListFragment fragment = new CustomerListFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomerPresenter = new CustomerPresenterImpl(this);
        mType = getArguments().getInt("type");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_list, null);

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
        mAdapter = new CustomerAdapter(getActivity().getApplicationContext());
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
                mCustomerPresenter.loadCustomers(mType, pageIndex);
            }
        }
    };

    private CustomerAdapter.OnItemClickListener mOnItemClickListener = new CustomerAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            CustomerBean customer = mAdapter.getItem(position);
            Intent intent = new Intent(getActivity(), CustomerDetailActivity.class);
            intent.putExtra("customer", customer);
            startActivity(intent);
        }
    };

    @Override
    public void onRefresh() {
        pageIndex = 0;
        if(mData != null) {
            mData.clear();
        }
        mCustomerPresenter.loadCustomers(mType, pageIndex);
    }

    @Override
    public void showProgress() {
        mSwipeRefreshWidget.setRefreshing(true);
    }

    @Override
    public void addCustomers(List<CustomerBean> customerList) {
        mAdapter.isShowFooter(true);
        if(mData == null) {
            mData = new ArrayList<>();
        }
        mData.addAll(customerList);
        if(pageIndex == 0) {
            mAdapter.setmData(mData);
        } else {
            if(customerList == null || customerList.size() == 0) {
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
