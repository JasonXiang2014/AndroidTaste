package jasonxiang.com.doityourself.xj.ui.recycler;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import jasonxiang.com.doityourself.R;
import jasonxiang.com.doityourself.xj.base.BaseActivity;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

/**
 * Created by xiangjian on 2016/11/12.
 */

public class PinterestMasonryAct extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.masonry_grid)
    RecyclerView mRecyclerView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_masonry;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("Staggered Grid");
        }

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        MasonryAdapter adapter = new MasonryAdapter(this);
        mRecyclerView.setAdapter(adapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        mRecyclerView.addItemDecoration(decoration);
        //TODO no effects
        mRecyclerView.setItemAnimator(new SlideInLeftAnimator());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //response to back arrow approach-1
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    //response to back arrow approach-2
//    @Override
//    public boolean onSupportNavigateUp() {
//        finish();
//        return super.onSupportNavigateUp();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        //response to back arrow approach-3
//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

}
