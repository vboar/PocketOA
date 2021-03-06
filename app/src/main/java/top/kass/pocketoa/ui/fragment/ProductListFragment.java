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
import top.kass.pocketoa.adapter.ProductAdapter;
import top.kass.pocketoa.bean.ProductBean;
import top.kass.pocketoa.presenter.ProductPresenter;
import top.kass.pocketoa.presenter.impl.ProductPresenterImpl;
import top.kass.pocketoa.ui.activity.ProductDetailActivity;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.view.ProductView;

public class ProductListFragment extends Fragment implements ProductView,
        SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ProductAdapter mAdapter;
    private List<ProductBean> mData;
    private ProductPresenter mProductPresenter;

    private int pageIndex = 0;
    private int sourceId;
    private int sourceType;

    public static ProductListFragment newInstance(int sourceId, int sourceType) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putInt("sourceId", sourceId);
        args.putInt("sourceType", sourceType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductPresenter = new ProductPresenterImpl(this);
        sourceId = getArguments().getInt("sourceId");
        sourceType = getArguments().getInt("sourceType");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_common_list, null);

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

        mAdapter = new ProductAdapter(getActivity().getApplicationContext());
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
                    mProductPresenter.loadProducts(pageIndex);
                } else {
                    mProductPresenter.loadProducts(sourceId, pageIndex);
                }
            }
        }
    };

    private ProductAdapter.OnItemClickListener mOnItemClickListener = new ProductAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            ProductBean product = mAdapter.getItem(position);
            Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
            intent.putExtra("productId", product.getProductId());
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
            mProductPresenter.loadProducts(pageIndex);
        } else {
            mProductPresenter.loadProducts(sourceId, pageIndex);
        }
    }

    @Override
    public void showProgress() {
        mSwipeRefreshWidget.setRefreshing(true);
    }

    @Override
    public void addProducts(List<ProductBean> productList) {
        mAdapter.isShowFooter(true);
        if(mData == null) {
            mData = new ArrayList<>();
        }
        mData.addAll(productList);
        if(pageIndex == 0) {
            if (mData.size() < 10) {
                mAdapter.isShowFooter(false);
            }
            mAdapter.setmData(mData);
        } else {
            if(productList == null || productList.size() == 0) {
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
        UIUtil.showSnackBar(view, getString(R.string.fail_loading), Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 1) {
            onRefresh();
        }
    }
}
