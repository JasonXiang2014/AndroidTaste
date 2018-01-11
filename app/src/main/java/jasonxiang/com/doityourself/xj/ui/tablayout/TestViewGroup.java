package jasonxiang.com.doityourself.xj.ui.tablayout;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by JasonXiang on 2016/12/21.
 */

public class TestViewGroup extends LinearLayout {

    public static final String TAG = TestViewGroup.class.getSimpleName();

    private int interceptX;
    private int interceptY;

    private int xLast;
    private int yLast;

    private int mPagingTouchSlop;
    private float mResistance = 1.7f;


    public TestViewGroup(Context context) {
        super(context);
    }

    public TestViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final ViewConfiguration conf = ViewConfiguration.get(getContext());
        mPagingTouchSlop = conf.getScaledTouchSlop() * 2;
    }

    @Override
    protected void onFinishInflate() {
        Log.d(TAG, "onFinishInflate is invoked");
        super.onFinishInflate();
    }

    public void setHeader(View header) {
        ViewGroup.LayoutParams lp = header.getLayoutParams();
        if (lp == null) {
            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            header.setLayoutParams(lp);
        }
        addView(header, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                interceptX = (int) ev.getX();
                interceptY = (int) ev.getY();
                xLast = (int) ev.getX();
                yLast = (int) ev.getY();
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:

                int deltaX = (int) (ev.getX() - interceptX);
                int deltaY = (int) (ev.getY() - interceptY);
                if (Math.abs(deltaY) > Math.abs(deltaX) && Math.abs(deltaY) > mPagingTouchSlop)
                    intercept = true;
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
            default:
                break;
        }
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:

                int deltaX = (int) (event.getX() - xLast);
                int deltaY = (int) (event.getY() - yLast);

                offsetTopAndBottom((int) (deltaY / mResistance));
                offsetLeftAndRight((int) (deltaX / mResistance));
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        xLast = (int) event.getX();
        yLast = (int) event.getY();
        return true;
    }
}
