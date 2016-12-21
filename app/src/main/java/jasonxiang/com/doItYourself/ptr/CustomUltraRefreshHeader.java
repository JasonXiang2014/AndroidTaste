package jasonxiang.com.doItYourself.ptr;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;
import jasonxiang.com.doItYourself.xj.common.widget.DiskView;

/**
 * Created by JasonXiang on 2016/12/21.
 */

public class CustomUltraRefreshHeader extends RelativeLayout implements PtrUIHandler {

    private DiskView mDiskView;
    private TextView mDescText;
    private ObjectAnimator anim;

    public CustomUltraRefreshHeader(Context context) {
        this(context, null, 0);
    }

    public CustomUltraRefreshHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomUltraRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, 100);
    }

    private void initView() {
        int diskWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        mDiskView = new DiskView(getContext());
        LinearLayout.LayoutParams diskParams = new LinearLayout.LayoutParams(diskWidth, diskWidth);
        mDiskView.setLayoutParams(diskParams);
        mDescText = new TextView(getContext());

        LinearLayout.LayoutParams descParams = new LinearLayout.LayoutParams(diskWidth * 3, ViewGroup.LayoutParams.WRAP_CONTENT);
        descParams.gravity = Gravity.CENTER;
        descParams.setMargins(diskWidth / 2, 0, 0, 0);
        mDescText.setLayoutParams(descParams);
        mDescText.setTextSize(12);
        mDescText.setTextColor(Color.GRAY);
        mDescText.setText("下拉刷新");

        LinearLayout ll = new LinearLayout(getContext());
        RelativeLayout.LayoutParams llParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llParams.addRule(CENTER_IN_PARENT);
        ll.setLayoutParams(llParams);
        ll.setPadding(10, 10, 10, 10);
        ll.addView(mDiskView);
        ll.addView(mDescText);
        addView(ll);
    }


    @Override
    public void onUIReset(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        anim = ObjectAnimator.ofFloat(mDiskView, "rotation", mDiskView.getRotation(), mDiskView.getRotation() + 360f)
                .setDuration(500);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.start();
        mDescText.setText("正在加载数据");
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        anim.cancel();
        mDescText.setText("加载完成");
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();
        int currentPosY = ptrIndicator.getCurrentPosY();//当前系统偏移值

        if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
            mDiskView.setRotation(currentPosY);
            if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
                mDescText.setText("下拉加载数据");
            } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
                mDescText.setText("松开加载更多");
            }
        }
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

    }
}
