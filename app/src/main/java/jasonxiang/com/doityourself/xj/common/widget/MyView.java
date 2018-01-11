package jasonxiang.com.doityourself.xj.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

public class MyView extends View {

    public static final String TAG = MyView.class.getSimpleName();

    private Paint paint;
    private Paint textPaint;

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        Log.e(TAG, "widthMeasureSpec = " + MeasureSpec.toString(widthMeasureSpec)
                + "heightMeasureSpec = " + MeasureSpec.toString(heightMeasureSpec));

       /* if (widthMode == MeasureSpec.AT_MOST) {
            width = 400;
            widthMode = MeasureSpec.EXACTLY;
        }*/
        if (heightMode == MeasureSpec.AT_MOST) {
            height = 400;
            heightMode = MeasureSpec.EXACTLY;

        }
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, widthMode);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, heightMode);

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    private void init(){
        //禁用GPU硬件加速，切换到软件渲染模式
        if(Build.VERSION.SDK_INT >= 11){
            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
        paint.setTextSize(20);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 40,
                getContext().getResources().getDisplayMetrics()));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawText(canvas);
        int left = getPaddingLeft();
        int right = getPaddingRight();
        int top = getPaddingTop();
        int bottom = getPaddingBottom();

        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        int floatCx = (getMeasuredWidth() - left - right) / 2 + left;

        int floatCy = (getMeasuredHeight() - top - bottom) / 2 + top;

        int radius;
        if (measuredWidth >= measuredHeight) {
            radius = (measuredHeight - top - bottom) / 2;
        } else {
            radius = (measuredWidth - left - right) / 2;
        }

        int layerId = canvas.saveLayer(0, 0, measuredWidth, measuredHeight, paint, Canvas.ALL_SAVE_FLAG);

        canvas.drawCircle(floatCx, floatCy, radius, paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(20);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Bitmap destBm = createSrcBitmap(measuredWidth, measuredHeight / 3);
        canvas.drawBitmap(destBm, 0, measuredHeight * 2 / 3, paint );
        paint.setXfermode(null);
        canvas.restoreToCount(layerId);

        paint.setTextAlign(Paint.Align.CENTER);
        canvas.save();
        canvas.translate(measuredWidth / 2, getPaddingTop() + radius/3 * 2);
        canvas.drawText("Hello,World", 0, 0, paint);
        canvas.translate(0, radius/3);
        canvas.drawText("你是我的小呀小苹果", 0, 0, paint);
        canvas.restore();
    }

    private Bitmap createSrcBitmap(int w, int h){
        Bitmap bitmap = Bitmap.createBitmap(w, h , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);
        canvas.drawRect(0 , 0 , w , h, paint);
        return bitmap;
    }

    private void drawText(Canvas canvas){
        String testText = "where fuking are you going?";
        int textSize = 40;
        canvas.drawText(testText, 0, textSize, textPaint);

//        Rect rect = new Rect();
//        textPaint.getTextBounds(testText, 0, testText.length(), rect);
//        canvas.drawText(testText,0,rect.height() ,textPaint);
    }

}
