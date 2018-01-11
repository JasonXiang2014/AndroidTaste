package jasonxiang.com.doityourself.xj.common.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import jasonxiang.com.doityourself.xj.common.utils.Log;

/**
 * Created by JasonXiang on 2016/12/19.
 */

//refer https://github.com/singwhatiwanna/android-art-res/blob/master/Chapter_4/src/com/ryg/chapter_4/ui/HorizontalScrollViewEx.java

public class HorizontalScrollViewEx extends ViewGroup {

    private static final String TAG = HorizontalScrollViewEx.class.getSimpleName();
    private int mChildWidth;
    private int mChildHeight;
    private int mChildIndex;
    private int mChildCount;

    //记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;

    // 分别记录上次滑动的坐标(onInterceptTouchEvent)
    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;


    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    public HorizontalScrollViewEx(Context context) {
        super(context, null);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (mScroller == null) {
            mScroller = new Scroller(getContext());
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth;
        int measureHeight;

        final int childCount = getChildCount();
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        if (childCount == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            measureWidth = getChildAt(0).getMeasuredWidth() * childCount;
            measureHeight = getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(measureWidth, measureHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            measureWidth = getChildAt(0).getMeasuredWidth() * childCount;
            setMeasuredDimension(measureWidth, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            measureWidth = getChildAt(0).getMeasuredWidth() * childCount;
            measureHeight = getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(measureWidth, measureHeight);
        }
    }

    @Override
    protected void onLayout(boolean b, int left, int top, int right, int bottom) {
        int childLeft = 0;
        final int childCount = getChildCount();
        mChildCount = childCount;
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            mChildWidth = childWidth;
            if (child.getVisibility() != View.GONE) {
                child.layout(childLeft, 0, childLeft + childWidth, child.getMeasuredHeight());
                childLeft += childWidth;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercept = true;
                } else {
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;
        //move的时候事件如果被截断 mLastX 只会被赋值两次(Down一次， Move一次)，intercept不会再被调用
        //直接走onTouchEvent
        Log.e(TAG, "intercept mLastX = " + mLastX + " mLastY = " + mLastY);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mVelocityTracker.addMovement(ev);
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        Log.e(TAG, "onTouchEvent x = " + x + " y = " + y);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                scrollBy(-deltaX, 0);
                break;
            case MotionEvent.ACTION_UP:
                // getScrollX就是拿的mScrollX，内容往右移就是正值，和画布移动方向一致
                int scrollX = getScrollX();
                Log.e(TAG, "scrollX = " + scrollX);
                mVelocityTracker.computeCurrentVelocity(1000);//计算速率
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) >= 50) {
                    mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
                } else {
                    mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }
                mChildIndex = Math.max(0, Math.min(mChildIndex, mChildCount - 1));
                int dx = mChildIndex * mChildWidth - scrollX;
                smoothScrollBy(dx, 0);
                mVelocityTracker.clear();
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothScrollBy(int deltaX, int deltaY) {
        mScroller.startScroll(getScrollX(), getScrollY(), deltaX, deltaY, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}
