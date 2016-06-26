package top.kass.pocketoa.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import top.kass.pocketoa.R;
import top.kass.pocketoa.ui.fragment.ContactListFragment;
import top.kass.pocketoa.ui.fragment.ContractListFragment;

public class ContractActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private int sourceId;
    private int sourceType;

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

        sourceId = getIntent().getIntExtra("sourceId", 0);
        sourceType = getIntent().getIntExtra("sourceType", 0);

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
            Intent intent = new Intent(this, ContractAddActivity.class);
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    private void reloadFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,
                ContractListFragment.newInstance(0, sourceId, sourceType)).commitAllowingStateLoss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            reloadFragment();
        }
    }

}
