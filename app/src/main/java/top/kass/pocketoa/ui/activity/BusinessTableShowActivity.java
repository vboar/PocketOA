package top.kass.pocketoa.ui.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.umeng.analytics.MobclickAgent;

import top.kass.pocketoa.R;
import top.kass.pocketoa.presenter.TableShowPresenter;
import top.kass.pocketoa.presenter.impl.TableShowPresenterImpl;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.view.TableShowView;

public class BusinessTableShowActivity extends AppCompatActivity implements TableShowView {

    private Toolbar mToolBar;
    private MaterialSpinner spType;
    private MaterialSpinner spYear;
    private MaterialSpinner spMonth;

    private ProgressDialog mProgressDialog;

    private TextView tv11;
    private TextView tv21;
    private TextView tv31;
    private TextView tv41;
    private TextView tv51;
    private TextView tv61;
    private TextView tv71;
    private TextView tv12;
    private TextView tv22;
    private TextView tv32;
    private TextView tv42;
    private TextView tv52;
    private TextView tv62;
    private TextView tv72;

    private int type;
    private int year;
    private int month;
    private int staffId;

    private TableShowPresenter mTableShowPresener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_table_show);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spType = (MaterialSpinner) findViewById(R.id.spType);
        spYear = (MaterialSpinner) findViewById(R.id.spYear);
        spMonth = (MaterialSpinner) findViewById(R.id.spMonth);

        spType.setItems("全部", "自己");
        spYear.setItems("2016年", "2015年");
        final String[] temp = new String[12];
        for (int i = 0; i < 12; i++) {
            temp[i] = Integer.toString(i+1) + "月";
        }
        spMonth.setItems(temp);
        spMonth.setSelectedIndex(5);
        spType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position == 0) type = 1;
                else type = 0;
                mTableShowPresener.showTable(type, staffId, year, month);
            }
        });
        spYear.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position == 0) year = 2016;
                else year = 2015;
                mTableShowPresener.showTable(type, staffId, year, month);
            }
        });
        spMonth.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                month = position + 1;
                mTableShowPresener.showTable(type, staffId, year, month);
            }
        });

        tv11 = (TextView) findViewById(R.id.tv11);
        tv21 = (TextView) findViewById(R.id.tv21);
        tv31 = (TextView) findViewById(R.id.tv31);
        tv41 = (TextView) findViewById(R.id.tv41);
        tv51 = (TextView) findViewById(R.id.tv51);
        tv61 = (TextView) findViewById(R.id.tv61);
        tv71 = (TextView) findViewById(R.id.tv71);
        tv12 = (TextView) findViewById(R.id.tv12);
        tv22 = (TextView) findViewById(R.id.tv22);
        tv32 = (TextView) findViewById(R.id.tv32);
        tv42 = (TextView) findViewById(R.id.tv42);
        tv52 = (TextView) findViewById(R.id.tv52);
        tv62 = (TextView) findViewById(R.id.tv62);
        tv72 = (TextView) findViewById(R.id.tv72);

        type = 1;
        year = 2016;
        month = 6;
        SharedPreferences sharedPreferences = getSharedPreferences("oa", MODE_PRIVATE);
        staffId =  sharedPreferences.getInt("staffId", 0);

        mTableShowPresener = new TableShowPresenterImpl(this);
        mTableShowPresener.showTable(type, staffId, year, month);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void showProgress() {
        mProgressDialog = ProgressDialog.show(this, "", "正在加载...", false, true);
    }

    @Override
    public void showTable(int[] data1, double[] data2) {
        tv11.setText(Integer.toString(data1[1]));
        tv21.setText(Integer.toString(data1[2]));
        tv31.setText(Integer.toString(data1[3]));
        tv41.setText(Integer.toString(data1[4]));
        tv51.setText(Integer.toString(data1[5]));
        tv61.setText(Integer.toString(data1[6]));
        tv71.setText(Integer.toString(data1[7]));
        tv12.setText(Double.toString(data2[1]));
        tv22.setText(Double.toString(data2[2]));
        tv32.setText(Double.toString(data2[3]));
        tv42.setText(Double.toString(data2[4]));
        tv52.setText(Double.toString(data2[5]));
        tv62.setText(Double.toString(data2[6]));
        tv72.setText(Double.toString(data2[7]));
    }

    @Override
    public void hideProgress() {
        mProgressDialog.hide();
        mProgressDialog.dismiss();
    }

    @Override
    public void showLoadFailMsg() {
        View view = findViewById(R.id.table_show_layout);
        UIUtil.showSnackBar(view, "加载失败", Snackbar.LENGTH_SHORT);
    }
}
