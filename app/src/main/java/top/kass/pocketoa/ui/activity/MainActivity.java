package top.kass.pocketoa.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;
import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.StaffBean;
import top.kass.pocketoa.presenter.MainPresenter;
import top.kass.pocketoa.presenter.impl.MainPresenterImpl;
import top.kass.pocketoa.ui.fragment.BusinessFragment;
import top.kass.pocketoa.ui.fragment.ContactFragment;
import top.kass.pocketoa.ui.fragment.ContractFragment;
import top.kass.pocketoa.ui.fragment.CustomerFragment;
import top.kass.pocketoa.ui.fragment.MainFragment;
import top.kass.pocketoa.ui.fragment.OpportunityFragment;
import top.kass.pocketoa.ui.fragment.ProductFragment;
import top.kass.pocketoa.util.UIUtil;
import top.kass.pocketoa.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    private Toolbar mToolBar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private View mHeaderView;
    private MainPresenter mMainPresenter;

    private static final int HOME = 0;
    private static final int CUSTOMER = 1;
    private static final int CONTACT = 2;
    private static final int OPPORTUNITY = 3;
    private static final int CONTRACT = 4;
    private static final int PRODUCT = 5;
    private static final int BUSINESS = 6;
    private int type = HOME;

    private long firstTime = 0;

    private StaffBean mStaffBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent(mNavigationView);

        mHeaderView = mNavigationView.getHeaderView(0);
        mHeaderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToUserEdit();
            }
        });

        mMainPresenter = new MainPresenterImpl(this);

        switchToHome();

        StaffBean staffBean = (StaffBean) getIntent().getSerializableExtra("staffBean");
        reloadStaffInfo(staffBean);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                firstTime = secondTime;
                View view = findViewById(R.id.drawer_layout);
                UIUtil.showSnackBar(view, getString(R.string.back_pressed_again), Snackbar.LENGTH_SHORT);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        switch (type) {
            case CUSTOMER:
            case CONTACT:
            case OPPORTUNITY:
            case CONTRACT:
            case PRODUCT:
                getMenuInflater().inflate(R.menu.common, menu);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent;
            switch (type) {
                case CUSTOMER:
                    intent = new Intent(getApplicationContext(), CustomerAddActivity.class);
                    startActivity(intent);
                    break;
                case CONTACT:
                    intent = new Intent(getApplicationContext(), ContactAddActivity.class);
                    startActivity(intent);
                    break;
                case OPPORTUNITY:
                    intent = new Intent(getApplicationContext(), OpportunityAddActivity.class);
                    startActivity(intent);
                    break;
                case CONTRACT:
                    intent = new Intent(getApplicationContext(), ContractAddActivity.class);
                    startActivity(intent);
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        mMainPresenter.switchNavigation(item.getItemId());
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );
    }

    @Override
    public void switchToHome() {
        type = HOME;
        mNavigationView.setCheckedItem(R.id.nav_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new MainFragment()).commit();
        mToolBar.setTitle(R.string.title_home);
        invalidateOptionsMenu();
    }

    @Override
    public void switchToCustomer() {
        if (type == CUSTOMER) return;
        type = CUSTOMER;
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,
                new CustomerFragment()).commit();
        mToolBar.setTitle(R.string.title_customer);
        invalidateOptionsMenu();
    }

    @Override
    public void switchToContact() {
        if (type == CONTACT) return;
        type = CONTACT;
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,
                new ContactFragment()).commit();
        mToolBar.setTitle(R.string.title_contact);
        invalidateOptionsMenu();
    }

    @Override
    public void switchToOpportunity() {
        if (type == OPPORTUNITY) return;
        type = OPPORTUNITY;
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,
                new OpportunityFragment()).commit();
        mToolBar.setTitle(R.string.title_opportunity);
        invalidateOptionsMenu();
    }

    @Override
    public void switchToContract() {
        if (type == CONTRACT) return;
        type = CONTRACT;
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,
                new ContractFragment()).commit();
        mToolBar.setTitle(R.string.title_contract);
        invalidateOptionsMenu();
    }

    @Override
    public void switchToProduct() {
        if (type == PRODUCT) return;
        type = PRODUCT;
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,
                new ProductFragment()).commit();
        mToolBar.setTitle(R.string.title_product);
        invalidateOptionsMenu();
    }

    @Override
    public void switchToBusiness() {
        if (type == BUSINESS) return;
        type = BUSINESS;
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,
                new BusinessFragment()).commit();
        mToolBar.setTitle(R.string.title_business);
        invalidateOptionsMenu();
    }

    @Override
    public void switchToLogout() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void reloadStaffInfo(StaffBean staffBean) {
        this.mStaffBean = staffBean;
        CircleImageView ivIcon = (CircleImageView) mHeaderView.findViewById(R.id.ivIcon);
        TextView tvName = (TextView) mHeaderView.findViewById(R.id.tvName);
        TextView tvEmail = (TextView) mHeaderView.findViewById(R.id.tvEmail);
        if (staffBean.getAvatar().equals("")) {
            ivIcon.setImageResource(R.drawable.ic_face_white_48dp);
        } else {
            Glide.with(this).load(staffBean.getAvatar()).crossFade()
                    .error(R.drawable.icon_default)
                    .into(ivIcon);
        }
        tvName.setText(staffBean.getName());
        tvEmail.setText(staffBean.getEmail());
    }

    @Override
    public void navigateToUserEdit() {
        Intent intent = new Intent(MainActivity.this, UserEditActivity.class);
        intent.putExtra("staffBean", mStaffBean);
        startActivity(intent);
    }

}
