package jasonxiang.com.doityourself.xj.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by JasonXiang on 2016/12/24. http://blog.sina.com.cn/s/blog_783ede0301012im3.html
 */

public class ArcView extends View {

    private Paint[] mPaints;
    private Boolean[] mUseCenters;
    private RectF mBigOval;
    private Paint rectPaint;
    private RectF[] mOvals;


    public ArcView(Context context){
        this(context, null);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaints = new Paint[4];
        mUseCenters = new Boolean[4];
//        1. 填充圆弧但不含圆心：
        mPaints[0] = new Paint();
        mPaints[0].setAntiAlias(true);
        mPaints[0].setStyle(Paint.Style.FILL);
        mPaints[0].setColor(0x88FF0000);
        mUseCenters[0] = false;
//        2. 填充圆弧带圆心（扇形）
        mPaints[1] = new Paint(mPaints[0]);
        mPaints[1].setColor(0x8800FF00);
        mUseCenters[1] = true;
//        3. 只绘圆周，不含圆心
        mPaints[2] = new Paint(mPaints[0]);
        mPaints[2].setStyle(Paint.Style.STROKE);
        mPaints[2].setStrokeWidth(4);
        mPaints[2].setColor(0x880000FF);
        mUseCenters[2] = false;
//        4. 只绘圆周，带圆心（扇形）
        mPaints[3] = new Paint(mPaints[2]);
        mPaints[3].setColor(0x88888888);
        mUseCenters[3] = true;

        rectPaint = new Paint();
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(2);
        rectPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int rectFWidth, rectFHeight ;
        int mBigOvalMarinTop = 20;
        rectFWidth = rectFHeight = w * 2 / 3;
        mBigOval = new RectF(w * 1 / 3 / 2, mBigOvalMarinTop, w * 1 / 3 / 2 + rectFWidth, mBigOvalMarinTop + rectFHeight);

        mOvals = new RectF[4];
        int mOvalsPadding = 40;
        int mOvalMarginLeft, mOvalMarginRight;
        mOvalMarginLeft = mOvalMarginRight = 20;

        int mOvalWidth = (w - mOvalMarginLeft - mOvalMarginRight - mOvalsPadding * 3) / 4;
        for(int i = 0 ; i < 4; i ++){
            mOvals[i] = new RectF(mOvalMarginLeft + (mOvalWidth + mOvalsPadding) * i,
                    mBigOvalMarinTop*2  + rectFHeight,
                    mOvalMarginLeft + (mOvalWidth + mOvalsPadding) * i + mOvalWidth,
                    mBigOvalMarinTop*2  + rectFHeight + mOvalWidth );
        }

    }

    private int mBigIndex = 0;
    private float SWEEP_INC = 1;
    private float mStart = 359;
    private float START_INC = 1;
    private float mSweep = mStart + 60;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        drawArcs(canvas, mBigOval, mUseCenters[mBigIndex],
                mPaints[mBigIndex]);
        for (int i = 0; i < 4; i++) {
            drawArcs(canvas, mOvals[i], mUseCenters[i], mPaints[i]);
        }
        mSweep += SWEEP_INC;
        if (mSweep > 360) {
            mSweep -= 360;
            mStart += START_INC;
            if (mStart >= 360) {
                mStart -= 360;
            }
            mBigIndex = (mBigIndex + 1) % mOvals.length;
        }
        invalidate();
    }

    private void drawArcs(Canvas canvas , RectF rectF, boolean useCenter, Paint paint){
        canvas.drawArc(rectF, mStart , mSweep, useCenter, paint);
        canvas.drawRect(rectF, rectPaint);
    }
}
