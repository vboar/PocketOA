package top.kass.pocketoa.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import top.kass.pocketoa.R;

public class ContractFragment extends Fragment {

    public static final int CONTRACT_MY = 0;
    public static final int CONTRACT_ALL = 1;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public static ContractListFragment newInstance(int type) {
        Bundle args = new Bundle();
        ContractListFragment fragment = new ContractListFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_common, null);

        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(2);
        setupViewPager(mViewPager);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_my_contracts));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_all_contracts));
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    private void setupViewPager(ViewPager mViewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.addFragment(ContractListFragment.newInstance(CONTRACT_MY), getString(R.string.tab_my_contracts));
        adapter.addFragment(ContractListFragment.newInstance(CONTRACT_ALL), getString(R.string.tab_all_contracts));
        mViewPager.setAdapter(adapter);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

}
