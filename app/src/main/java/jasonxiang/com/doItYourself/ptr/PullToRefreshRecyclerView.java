package jasonxiang.com.doItYourself.ptr;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


/**
 * Created by WeiDongliang on 2016/6/13.
 */
public class PullToRefreshRecyclerView extends PullToRefreshBase {

    private static final int DEFAULT_LOAD_MORE_REMAIN_COUNT = 3;

    private FrameLayout mContent;
    private RecyclerView mRecyclerView;

    private OnRefreshListener mListener;
    private OnLoadMoreListener mOnLoadMoreListener;
    private int mLoadMoreRemainCount = DEFAULT_LOAD_MORE_REMAIN_COUNT;

    public PullToRefreshRecyclerView(Context context) {
        this(context, null);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mContent = new FrameLayout(getContext());
        this.addViewForPtrFrameLayout(mContent);
        mRecyclerView = new RecyclerView(getContext(),attrs);
        mRecyclerView.setId(android.R.id.list);
        mContent.addView(mRecyclerView);

        mRecyclerView.setOnScrollListener(new MYOnScrollListener());

        this.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame,mRecyclerView,header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (mListener != null) {
                    mListener.onRefresh(PullToRefreshRecyclerView.this);
                }
            }
        });
    }

    public final RecyclerView getRefreshableView () {
        return mRecyclerView;
    }

    public void setAdapter (Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setRefreshing() {
        mRecyclerView.smoothScrollToPosition(0);
        super.setRefreshing();
    }

    @Override
    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mListener = listener;
    }

    public void setLoadMoreRemainCount (int count) {
        mLoadMoreRemainCount = count;
    }

    public void setOnLoadMoreListener (OnLoadMoreListener listener) {
        this.mOnLoadMoreListener = listener;
    }

    public class MYOnScrollListener extends OnScrollListener{
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE && mOnLoadMoreListener != null) {
                int lastPosition = getLastVisiblePosition();
                int total = mRecyclerView.getAdapter().getItemCount();
                if (total > 10 && total - lastPosition <= mLoadMoreRemainCount && getHeader().getTop() == -getHeader().getHeight()) {
                    mOnLoadMoreListener.onLoadMore();
                }
            }
        }
    }

    /**
     * 获取最后一条展示的位置
     *
     * @return
     */
    private int getLastVisiblePosition() {
        int position;
        RecyclerView.LayoutManager layoutManager = getRefreshableView().getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof GridLayoutManager) {
            position = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager sLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int[] lastPositions = sLayoutManager.findLastVisibleItemPositions(new int[sLayoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = layoutManager.getItemCount() - 1;
        }
        return position;
    }

    /**
     * 获得最大的位置
     *
     * @param positions
     * @return
     */
    private int getMaxPosition(int[] positions) {
        int size = positions.length;
        int maxPosition = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            maxPosition = Math.max(maxPosition, positions[i]);
        }
        return maxPosition;
    }
}
