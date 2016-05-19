package top.kass.pocketoa.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import top.kass.pocketoa.R;

public class CustomerListFragment extends Fragment {

    public static CustomerListFragment newInstance(int type) {
        Bundle args = new Bundle();
        CustomerListFragment fragment = new CustomerListFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_list, null);
        return view;
    }
}
