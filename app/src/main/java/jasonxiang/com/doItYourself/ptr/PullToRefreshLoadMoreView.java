package jasonxiang.com.doItYourself.ptr;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import jasonxiang.com.doItYourself.R;
import jasonxiang.com.doItYourself.xj.common.utils.UIUtils;

/**
 * Created by guomenglong on 16/8/4.
 */

public class PullToRefreshLoadMoreView extends LinearLayout {
    private ProgressBar mLoading;
    private TextView mLoadingText;
    private Context mContext;

    public PullToRefreshLoadMoreView(Context context) {
        this(context, null);
        mContext = context;
    }

    public PullToRefreshLoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        mContext = context;
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.product_search_load_more, this);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int px20 = UIUtils.dp2px(10);
        setPadding(px20, px20, px20, px20);
        mLoading = (ProgressBar) findViewById(R.id.loading_view);
        mLoadingText = (TextView) findViewById(R.id.loading_text);
    }

    public void isLoading(boolean loading) {
        if (loading) {
            mLoading.setVisibility(VISIBLE);
            mLoadingText.setText(R.string.pull_to_refresh_more);
        } else {
            mLoading.setVisibility(GONE);
            mLoadingText.setText(R.string.pull_to_refresh_no_more);
        }
    }

    public void initLoadMoreView() {
        this.setVisibility(GONE);
        mLoading.setVisibility(VISIBLE);
        mLoadingText.setText(mContext.getString(R.string.pull_to_refresh_more));
    }

    public void loadMoreComplete() {
        this.setVisibility(VISIBLE);
        mLoading.setVisibility(GONE);
        mLoadingText.setText(mContext.getString(R.string.pull_to_refresh_no_more));
    }
}
