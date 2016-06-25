package top.kass.pocketoa.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import top.kass.pocketoa.R;

public class ProductFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_product, null);
        getFragmentManager().beginTransaction().
                replace(R.id.frame_content, ProductListFragment.newInstance()).commit();
        return view;
    }

}
