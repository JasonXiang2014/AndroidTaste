package jasonxiang.com.doityourself.xj.ui.list;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import jasonxiang.com.doityourself.R;
import jasonxiang.com.doityourself.ptr.CustomUltraRefreshHeader;
import jasonxiang.com.doityourself.ptr.UltraRefreshListView;
import jasonxiang.com.doityourself.ptr.UltraRefreshListener;
import jasonxiang.com.doityourself.xj.base.BaseActivity;

/**
 * Created by JasonXiang on 2016/12/21.
 */

public class UltraListActivity extends BaseActivity implements UltraRefreshListener {

    private PtrFrameLayout mPtrFrame;

    private List<String> datas = new ArrayList<>();

    private ArrayAdapter<String> mAdapter;

    private UltraRefreshListView ultraRefreshListView;

    private static final int PAGE_COUNT = 10;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_utral_list;
    }

    /**
     * PtrUIHandler 是用来控制整个下拉过程头部的回调
     * <p>
     * PtrHandler 是用来检查能否下拉刷新和监听下拉刷新刚开始时的回调
     */
    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
        mPtrFrame = ((PtrFrameLayout) findViewById(R.id.ultra_ptr));

        //创建我们的自定义头部视图
        CustomUltraRefreshHeader header = new CustomUltraRefreshHeader(this);
        mPtrFrame.setHeaderView(header);
        //设置视图修改的回调，因为我们的CustomUltraRefreshHeader实现了PtrUIHandler
        mPtrFrame.addPtrUIHandler(header);

        ultraRefreshListView = ((UltraRefreshListView) findViewById(R.id.ultra_lv));
        //设置数据刷新的回调，因为UltraRefreshListView实现了PtrHandler
        mPtrFrame.setPtrHandler(ultraRefreshListView);

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
        ultraRefreshListView.setAdapter(mAdapter);
        //设置数据刷新回调接口
        ultraRefreshListView.setUltraRefreshListener(this);
    }

    private void initData() {
        for (int i = 0; i < PAGE_COUNT; i++) {
            datas.add("添加了数据~~ " + i);
        }
    }

    @Override
    public void onRefresh() {
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                datas.clear();
                for (int i = 0; i < 20; i++) {
                    datas.add("添加了数据~~" + i);
                }
                //刷新完成
                ultraRefreshListView.refreshComplete();
                mAdapter.notifyDataSetChanged();
            }
        }, 1000);

    }

    @Override
    public void loadMore() {
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                int count = mAdapter.getCount();
                for (int i = count; i < count + 10; i++) {
                    datas.add("添加了数据~~" + i);
                }
                mAdapter.notifyDataSetChanged();
                //刷新完成
                ultraRefreshListView.loadComplete();
            }
        }, 1000);

    }

}
