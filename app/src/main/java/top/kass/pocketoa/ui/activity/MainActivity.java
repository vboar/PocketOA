package top.kass.pocketoa.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import top.kass.pocketoa.R;
import top.kass.pocketoa.presenter.MainPresenter;
import top.kass.pocketoa.presenter.impl.MainPresenterImpl;
import top.kass.pocketoa.ui.fragment.BusinessFragment;
import top.kass.pocketoa.ui.fragment.ContactFragment;
import top.kass.pocketoa.ui.fragment.ContractFragment;
import top.kass.pocketoa.ui.fragment.CustomerFragment;
import top.kass.pocketoa.ui.fragment.MainFragment;
import top.kass.pocketoa.ui.fragment.OpportunityFragment;
import top.kass.pocketoa.ui.fragment.ProductFragment;
import top.kass.pocketoa.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    private Toolbar mToolBar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private MainPresenter mMainPresenter;

    private static final int HOME = 0;
    private static final int CUSTOMER = 1;
    private static final int CONTACT = 2;
    private static final int OPPORTUNITY = 3;
    private static final int CONTRACT = 4;
    private static final int PRODUCT = 5;
    private static final int BUSSINESS = 0;
    private int type = HOME;

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

        mMainPresenter = new MainPresenterImpl(this);

        switchToHome();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        switch (type) {
            case CUSTOMER:
                getMenuInflater().inflate(R.menu.customer, menu);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (type == CUSTOMER && id == R.id.action_add) {
            Intent intent = new Intent(getApplicationContext(), CustomerAddActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
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
        if (type == BUSSINESS) return;
        type = BUSSINESS;
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,
                new BusinessFragment()).commit();
        mToolBar.setTitle(R.string.title_business);
        invalidateOptionsMenu();
    }

    @Override
    public void switchToLogout() {
        // TODO logout bl
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
