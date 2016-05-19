package top.kass.pocketoa.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
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
import top.kass.pocketoa.ui.fragment.CustomerFragment;
import top.kass.pocketoa.ui.fragment.MainFragment;
import top.kass.pocketoa.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    private Toolbar mToolBar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private MainPresenter mMainPresenter;

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void switchToHome() {
        mNavigationView.setCheckedItem(R.id.nav_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new MainFragment()).commit();
        mToolBar.setTitle(R.string.title_home);
    }

    @Override
    public void switchToCustomer() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,
                new CustomerFragment()).commit();
        mToolBar.setTitle(R.string.title_customer);
    }

    @Override
    public void switchToContact() {
        mToolBar.setTitle(R.string.title_contact);
    }

    @Override
    public void switchToOpportunity() {
        mToolBar.setTitle(R.string.title_opportunity);
    }

    @Override
    public void switchToContract() {
        mToolBar.setTitle(R.string.title_contract);
    }

    @Override
    public void switchToProduct() {
        mToolBar.setTitle(R.string.title_product);
    }

    @Override
    public void switchToBusiness() {
        mToolBar.setTitle(R.string.title_business);
    }

    @Override
    public void switchToLogout() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
