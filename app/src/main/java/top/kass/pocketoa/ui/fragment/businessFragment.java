package top.kass.pocketoa.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import top.kass.pocketoa.R;
import top.kass.pocketoa.ui.activity.BusinessAttentionSettingActivity;
import top.kass.pocketoa.ui.activity.BusinessTableShowActivity;

public class BusinessFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_business, null);

        Button btnGoalSetting = (Button) view.findViewById(R.id.btn_goal_setting);
        btnGoalSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BusinessAttentionSettingActivity.class);
                startActivity(intent);
            }
        });

        Button btnAttentionSetting = (Button) view.findViewById(R.id.btn_attention_setting);
        btnAttentionSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BusinessAttentionSettingActivity.class);
                startActivity(intent);
            }
        });

        Button btnTableShow = (Button) view.findViewById(R.id.btn_table_show);
        btnTableShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BusinessTableShowActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
