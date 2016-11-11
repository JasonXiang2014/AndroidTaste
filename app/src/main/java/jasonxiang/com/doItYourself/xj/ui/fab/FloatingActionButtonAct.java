package jasonxiang.com.doItYourself.xj.ui.fab;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
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
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.main_container)
    CoordinatorLayout parentView;

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

    @OnClick(R.id.fab)
    public void snackBar() {
        MyOnClickListener myOnClickListener = new MyOnClickListener();
        Snackbar.make(parentView, R.string.snackbar_text, Snackbar.LENGTH_LONG)
                .setAction(R.string.snackbar_action, myOnClickListener)
                .setActionTextColor(ContextCompat.getColor(FloatingActionButtonAct.this, R.color.blue))
                .show(); // Don’t forget to show!
//        Snackbar.make(parentView, R.string.snackbar_text, Snackbar.LENGTH_INDEFINITE).show();
    }

    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Toast.makeText(FloatingActionButtonAct.this, "嗯哼？", Toast.LENGTH_LONG).show();
        }
    }


}
