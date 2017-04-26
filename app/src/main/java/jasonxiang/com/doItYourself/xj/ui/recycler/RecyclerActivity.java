package jasonxiang.com.doItYourself.xj.ui.recycler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jasonxiang.com.doItYourself.R;
import jasonxiang.com.doItYourself.xj.common.recycler.DividerGridItemDecoration;
import jasonxiang.com.doItYourself.xj.common.recycler.HomeAdapter;
import jasonxiang.com.doItYourself.xj.common.recycler.StaggeredHomeAdapter;
import jasonxiang.com.doItYourself.xj.model.User;
import jasonxiang.com.doItYourself.xj.ui.main.Page1Fragment;

public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private HomeAdapter mAdapter;
    private StaggeredHomeAdapter mStaggeredHomeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recyclerview);

        initData();

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mAdapter = new HomeAdapter(this, mDatas);

/*        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL));*/
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        initEvent();

        Intent intent = getIntent();
        User user = intent.getParcelableExtra(Page1Fragment.EXTRA_USER);
        Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show();
    }

    private void initEvent() {
        mAdapter.setOnItemClickLitener(new HomeAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecyclerActivity.this, position + " click",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(RecyclerActivity.this, position + " long click",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void initData() {
        mDatas = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_action_add:
                mAdapter.addData(1);
                break;
            case R.id.id_action_delete:
                mAdapter.removeData(1);
                break;
            case R.id.id_action_gridview:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
                break;
            case R.id.id_action_listview:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.id_action_horizontalGridView:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
                        StaggeredGridLayoutManager.HORIZONTAL));
                break;
            case R.id.id_action_staggeredgridview:
                Intent intent = new Intent(this , StaggeredGridLayoutActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

}
