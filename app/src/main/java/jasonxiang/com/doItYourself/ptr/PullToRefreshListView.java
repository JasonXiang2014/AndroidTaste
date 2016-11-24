package jasonxiang.com.doItYourself.ptr;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by xiangjian on 2015/11/9.
 */
public class PullToRefreshListView extends PullToRefreshBase implements AbsListView.OnScrollListener {

    private static final int DEFAULT_LOAD_MORE_REMAIN_COUNT = 3;
    private FrameLayout mContent;
    private ListView mListView;
    private int mLoadMoreRemainCount = DEFAULT_LOAD_MORE_REMAIN_COUNT;

    private AbsListView.OnScrollListener mOnScrollListener;
    private OnLoadMoreListener mOnLoadMoreListener;
    private OnRefreshListener mListener;

    private PullToRefreshLoadMoreView mMoreView;
    //private LinearLayout mLoadingContent;
    private ProgressBar mLoadingBar;
    private TextView mLoadingText;

    private boolean mIsShowLoadMoreLoading;
    private boolean mManualShowLoadMoreLoading;

    private boolean allowLoadMore;
    private int totalAmount;

    public PullToRefreshListView(Context context) {
        this(context, null);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mContent = new FrameLayout(getContext());
        this.addViewForPtrFrameLayout(mContent);
        mListView = new ListView(getContext(), attrs);
        mListView.setFooterDividersEnabled(false);
        mListView.setId(android.R.id.list);
        mContent.addView(mListView);
        mListView.setOnScrollListener(this);

        this.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                View mContent = mListView.isShown() ? mListView :
                        mListView.getEmptyView() != null ? mListView.getEmptyView() : content;
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mContent, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (mListener != null) {
                    resetLoadMore();
                    mListener.onRefresh(PullToRefreshListView.this);
                }
            }

        });
    }

    private void initLoadMore() {
        mMoreView = new PullToRefreshLoadMoreView(getContext());
        mMoreView.initLoadMoreView();
    }

    private void resetLoadMore() {
        mMoreView.initLoadMoreView();
    }

    public void setTotalAmount(int amount) {
        this.totalAmount = amount;
    }

    private boolean isAllowLoadMore() {
        if (mListView.getAdapter() != null) {
            return mListView.getAdapter().getCount() < totalAmount;
        }
        return false;
    }

    @Override
    public void setRefreshing() {
        mListView.setSelection(0);
        super.setRefreshing();
    }

    public final ListView getRefreshableView() {
        return mListView;
    }

    public void setAdapter(ListAdapter adapter) {
        if (mIsShowLoadMoreLoading || mManualShowLoadMoreLoading) {
            initLoadMore();
            mListView.addFooterView(mMoreView);
        }
        mListView.setAdapter(adapter);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mListView.setOnItemClickListener(listener);
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener listener) {
        mListView.setOnItemLongClickListener(listener);
    }

    public void addHeaderView(View view, Object data, boolean isSelectable) {
        mListView.addHeaderView(view, data, isSelectable);
    }

    public void addHeaderView(View view) {
        mListView.addHeaderView(view);
    }

    public int getHeaderViewsCount() {
        return mListView.getHeaderViewsCount();
    }

    public void setEmptyView(View emptyView) {
        if (emptyView == null) {
            return;
        }
        if (emptyView.getParent() != null) {
            ((ViewGroup) emptyView.getParent()).removeView(emptyView);
        }
        if (emptyView.getVisibility() != VISIBLE) {
            emptyView.setVisibility(VISIBLE);
        }

        mContent.addView(emptyView);
        mListView.setEmptyView(emptyView);
    }

    @Override
    public void setOnRefreshListener(final OnRefreshListener refreshListener) {
        this.mListener = refreshListener;
    }

    public final void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mOnLoadMoreListener = listener;
    }

    public final void setLoadMoreRemainCount(int remainCount) {
        mLoadMoreRemainCount = remainCount;
    }

    public final void setOnScrollListener(AbsListView.OnScrollListener listener) {
        mOnScrollListener = listener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int state) {
        if (isAllowLoadMore() && state == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && null != mOnLoadMoreListener) {
            int lastPosition = getRefreshableView().getLastVisiblePosition();
            int total = getRefreshableView().getCount() - 1;
            if (total > 0 && total - lastPosition <= mLoadMoreRemainCount && getHeader().getTop() == -getHeader().getHeight()) {
                mOnLoadMoreListener.onLoadMore();
                if (mIsShowLoadMoreLoading) {
                    mMoreView.setVisibility(showMoreViewOnAutoMode() ? VISIBLE : GONE);
                }
            }
        }

        if (null != mOnScrollListener) {
            mOnScrollListener.onScrollStateChanged(view, state);
        }
    }

    public void onLoadMoreComplete(boolean isNoMoreData) {
        if (!mIsShowLoadMoreLoading && !mManualShowLoadMoreLoading) {
            return;
        }
        if (isNoMoreData && (showMoreViewOnAutoMode() || showMoreViewOnManualMode())) {

            mMoreView.loadMoreComplete();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // Finally call OnScrollListener if we have one
        if (null != mOnScrollListener) {
            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    public AbsListView.OnScrollListener getmOnScrollListener() {
        return mOnScrollListener;
    }

    public void showLoadMoreLoading() {
        if (mListView.getAdapter() != null) {
            return;
        }
        mIsShowLoadMoreLoading = true;
    }

    public void enableManualShowLoadMoreLoading() {
        if (mListView.getAdapter() != null) {
            return;
        }
        mManualShowLoadMoreLoading = true;
    }

    public void manualShowLoadMoreLoading(boolean show) {
        if (mManualShowLoadMoreLoading) {
            mMoreView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    private boolean showMoreViewOnManualMode() {
        return mManualShowLoadMoreLoading && mMoreView.getVisibility() == View.VISIBLE;
    }

    private boolean showMoreViewOnAutoMode() {
        if (!mIsShowLoadMoreLoading) {
            return false;
        }
        if (mMoreView.getVisibility() == View.VISIBLE) {
            return true;
        }
        int allItemHeight = 0;
        for (int i = 0; i < mListView.getChildCount(); i++) {
            allItemHeight += mListView.getChildAt(i).getHeight();
        }
        return allItemHeight >= mListView.getHeight();
    }
}
