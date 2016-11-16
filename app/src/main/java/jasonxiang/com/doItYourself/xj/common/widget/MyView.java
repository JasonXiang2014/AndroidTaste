package jasonxiang.com.doItYourself.xj.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyView extends View {

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        Log.e("test", "widthMeasureSpec = " + MeasureSpec.toString(widthMeasureSpec)
                + "heightMeasureSpec = " + MeasureSpec.toString(heightMeasureSpec));

        if (widthMode == MeasureSpec.AT_MOST) {
            width = 800;
            widthMode = MeasureSpec.EXACTLY;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            height = 400;
            heightMode = MeasureSpec.EXACTLY;

        }
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, widthMode);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, heightMode);

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int left = getPaddingLeft();
        int right = getPaddingRight();
        int top = getPaddingTop();
        int bottom = getPaddingBottom();
        Log.e("test", "paddingleft = " + left + "pr = " + right + "top =" + top + "bottom = " + bottom);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setAlpha(Color.parseColor("#ffffff"));


        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        int floatCx = (getMeasuredWidth() - left - right) / 2 + left;

        int floatCy = (getMeasuredHeight() - top - bottom) / 2 + top;

        int radius;
        if (measuredWidth > measuredHeight) {
            radius = (measuredHeight - top - bottom) / 2;
        } else {
            radius = (measuredWidth - left - right) / 2;
        }

        canvas.drawCircle(floatCx, floatCy, radius, paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        canvas.drawText("Hello,World", floatCx - radius * 1 / 3, floatCy - radius * 2 / 3, paint);
        canvas.drawText("你是我的小呀小苹果", floatCx - radius * 2 / 3, floatCy - radius * 1 / 3, paint);

        Bitmap target = Bitmap.createBitmap(radius * 2, radius * 2 / 3, Config.ARGB_8888);

        paint.setColor(Color.GREEN);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

//		canvas.drawRect(floatCx - radius, floatCy + radius * 1/ 3, 
//				floatCx + radius, floatCy + radius, paint);
        canvas.drawBitmap(target, floatCx - radius, floatCy + radius * 1 / 3, paint);
    }

}
