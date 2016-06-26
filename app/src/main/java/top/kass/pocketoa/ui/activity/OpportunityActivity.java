package top.kass.pocketoa.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import top.kass.pocketoa.R;
import top.kass.pocketoa.ui.fragment.OpportunityListFragment;

public class OpportunityActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private int customerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        customerId = getIntent().getIntExtra("customerId", 0);

        reloadFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent = new Intent(this, OpportunityAddActivity.class);
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    private void reloadFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,
                OpportunityListFragment.newInstance(0, customerId, 1)).commitAllowingStateLoss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            reloadFragment();
        }
    }

}