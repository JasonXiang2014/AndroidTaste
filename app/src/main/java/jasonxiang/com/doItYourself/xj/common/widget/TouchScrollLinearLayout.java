package jasonxiang.com.doItYourself.xj.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class TouchScrollLinearLayout extends LinearLayout {

    private View headerLayout;
    private ListView childListView;

    private LayoutParams headerLayoutParams;
    private Scroller scroller;

    private float dx, dy;
    private float lastY;
    private boolean captured;

    public TouchScrollLinearLayout(Context context) {
        super(context);
        init(context);
    }

    public TouchScrollLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TouchScrollLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        scroller = new Scroller(context, new DecelerateInterpolator());
    }

    /**
     * @param headerLayout 赋值要隐藏和显示的headerLayout
     */
    public void setHeaderView(View headerLayout) {
        this.headerLayout = headerLayout;
        headerLayoutParams = (LayoutParams) headerLayout.getLayoutParams();
    }

    /**
     * @param listView 如果header的隐藏和显示 要跟listview 配合，还要在这里加listView
     */

    public void setChildListView(ListView listView) {
        childListView = listView;
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return super.onInterceptHoverEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (headerLayout == null || childListView == null) {
            return super.dispatchTouchEvent(ev);
        }

        headerLayoutParams = (LayoutParams) headerLayout.getLayoutParams();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                scroller.abortAnimation();
                captured = false;
                dx = ev.getX();
                dy = ev.getY();
                lastY = ev.getRawY();
                return super.dispatchTouchEvent(ev);
            }
            case MotionEvent.ACTION_MOVE: {

                if (Math.abs(ev.getY() - dy) < Math.abs(ev.getX() - dx) / 2) {
                    break;
                }
                dx = ev.getX();
                dy = ev.getY();
                if (ev.getRawY() > lastY) {

                    if (childListView != null && childListView.getScrollY() == 0 && childListView.getFirstVisiblePosition() == 0) {
                        if (headerLayoutParams.topMargin == 0) {
                            lastY = ev.getRawY();
                            return captured = super.dispatchTouchEvent(ev);
                        }
                        headerLayoutParams.topMargin += ev.getRawY() - lastY;
                        if (headerLayoutParams.topMargin > 0) {
                            headerLayoutParams.topMargin = 0;
                        }
                        headerLayout.setLayoutParams(headerLayoutParams);
                        lastY = ev.getRawY();
                        return true;
                    }
                } else {

                    if (childListView != null && headerLayoutParams.topMargin > -headerLayout.getHeight()) {
                        if (headerLayoutParams.topMargin == -headerLayout.getHeight()) {
                            lastY = ev.getRawY();
                            return captured = super.dispatchTouchEvent(ev);
                        }
                        headerLayoutParams.topMargin += ev.getRawY() - lastY;
                        if (headerLayoutParams.topMargin < -headerLayout.getHeight()) {
                            headerLayoutParams.topMargin = -headerLayout.getHeight();
                        }
                        headerLayout.setLayoutParams(headerLayoutParams);
                        lastY = ev.getRawY();
                        return true;
                    }
                }

                break;
            }
            case MotionEvent.ACTION_UP: {
                if (headerLayoutParams.topMargin == 0 || headerLayoutParams.topMargin == -headerLayout.getHeight()) {
                    return super.dispatchTouchEvent(ev);
                }
                if (headerLayoutParams.topMargin < -headerLayout.getHeight() / 2) {
                    // 弹回
                    scroller.startScroll(0, headerLayoutParams.topMargin, 0, -headerLayoutParams.height - headerLayoutParams.topMargin);
                } else {
                    // 弹出
                    scroller.startScroll(0, headerLayoutParams.topMargin, 0, -headerLayoutParams.topMargin);
                }

                invalidate();
                break;
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            headerLayoutParams.topMargin = scroller.getCurrY();
            headerLayout.setLayoutParams(headerLayoutParams);
            headerLayout.postInvalidate();
        }
        super.computeScroll();
    }
}
