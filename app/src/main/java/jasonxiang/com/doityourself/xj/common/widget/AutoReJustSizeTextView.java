package jasonxiang.com.doityourself.xj.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by JasonXiang on 2016/12/22.
 */

public class AutoReJustSizeTextView extends TextView {

    public AutoReJustSizeTextView(Context context) {
        this(context, null);
    }

    public AutoReJustSizeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoReJustSizeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        refit(getText().toString(), getWidth());
    }

    private void refit(String str, int width){
        if(TextUtils.isEmpty(str) || width <= 0){
            return;
        }
        float desireTextSize = getTextSize();
        int availableWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        Paint mTextPaint = new Paint();
        mTextPaint.set(getPaint());
        float actualStrWidth =  mTextPaint.measureText(str);
        while (actualStrWidth > availableWidth ){
            desireTextSize -= 1;
            mTextPaint.setTextSize(desireTextSize);
            actualStrWidth = (int) mTextPaint.measureText(str);
        }
        setTextSize(TypedValue.COMPLEX_UNIT_PX, desireTextSize);
    }
}
