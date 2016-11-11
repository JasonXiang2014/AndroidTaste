package jasonxiang.com.doItYourself.xj.ui.fab;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jasonxiang.com.doItYourself.R;
import jasonxiang.com.doItYourself.xj.base.BaseActivity;
import jasonxiang.com.doItYourself.xj.recycler.DividerGridItemDecoration;
import jasonxiang.com.doItYourself.xj.recycler.HomeAdapter;

/**
 * Created by xiangjian on 2016/11/11.
 */

//refer:https://github.com/codepath/android_guides/wiki/Floating-Action-Buttons
public class FloatingActionButtonAct extends BaseActivity {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private List<String> mDatas;
    private HomeAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fab;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
        mAdapter = new HomeAdapter(this, mDatas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
    }

    protected void initData() {
        mDatas = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

}
