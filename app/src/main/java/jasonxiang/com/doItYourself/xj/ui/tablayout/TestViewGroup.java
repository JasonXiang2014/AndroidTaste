package jasonxiang.com.doItYourself.xj.ui.tablayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by JasonXiang on 2016/12/21.
 */

public class TestViewGroup extends LinearLayout {

    public static final String TAG = TestViewGroup.class.getSimpleName();

    public TestViewGroup(Context context) {
        super(context);
    }

    public TestViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        Log.d(TAG, "onFinishInflate is invoked");
        super.onFinishInflate();
    }

    public void setHeader(View header){
        ViewGroup.LayoutParams lp = header.getLayoutParams();
        if (lp == null) {
            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            header.setLayoutParams(lp);
        }
        addView(header, 0);
    }
}
