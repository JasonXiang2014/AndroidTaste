package jasonxiang.com.doityourself.ptr;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.ListView;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import jasonxiang.com.doityourself.R;

/**
 * Created by JasonXiang on 2016/12/21.
 */

//refer http://blog.csdn.net/lisdye2/article/details/51449716

public class UltraRefreshListView extends ListView implements PtrHandler, AbsListView.OnScrollListener {

    private UltraRefreshListener mUltraRefreshListener;

    /**
     * foot布局
     */
    private View footView;

    /**
     * 是否处于上拉加载
     */
    private boolean isLoading = false;

    /**
     * 是否处于下拉刷新
     */
    private boolean isRefreshing = false;

    public UltraRefreshListView(Context context) {
        this(context, null);
    }

    public UltraRefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UltraRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        footView = LayoutInflater.from(getContext()).inflate(R.layout.foot_ultra_refresh_listview, null);
        setOnScrollListener(this);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        isLoading = false;
        isRefreshing = true;
        //下拉刷新的回调
        if (mUltraRefreshListener != null) {
            mUltraRefreshListener.onRefresh();
        }
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        //  PtrHandler 的接口回调，是否能够加载数据的判断
        return !isLoading && !isRefreshing && PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    /**
     * 设置ListView的监听回调
     */
    public void setUltraRefreshListener(UltraRefreshListener mUltraRefreshListener) {
        this.mUltraRefreshListener = mUltraRefreshListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        /*Log.i("info","isLoadData:"+isLoadData+" totalItemCount "+totalItemCount+" firstVisibleItem "+
                firstVisibleItem+" visibleItemCount "+ visibleItemCount);
*/
        //加载更多的判断
        if (totalItemCount > 1 && !isLoading && totalItemCount == firstVisibleItem + visibleItemCount) {
            isRefreshing = false;
            isLoading = true;
            addFooterView(footView);
            mUltraRefreshListener.loadMore();
        }
    }

    //下拉刷新完成
    public void refreshComplete() {
        isRefreshing = false;
        //获取其父控件，刷新
        ViewParent parent = getParent();
        if (parent instanceof PtrFrameLayout) {
            ((PtrFrameLayout) parent).refreshComplete();
        }
    }

    //上拉加载完成
    public void loadComplete() {
        isLoading = false;
        removeFooterView(footView);
    }

}
