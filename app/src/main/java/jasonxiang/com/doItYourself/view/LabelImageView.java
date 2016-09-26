package jasonxiang.com.doItYourself.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

import jasonxiang.com.doItYourself.R;


/**
 * 自定义View，实现圆角，圆形等效果
 */
public class LabelImageView extends ImageView {

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLOR_DRAWABLE_DIMENSION = 2;

    private static final int INNER_PADDING = 16;

    /**
     * 图片
     */
    private Bitmap mSrc;

    /**
     * 字体大小
     */
    private int mTextSize;

    /**
     * 字
     */
    private String mText;

    /**
     * 控件的宽度
     */
    private int mWidth;
    /**
     * 控件的高度
     */
    private int mHeight;

    /**
     * 圆角画笔
     */
    private Paint imgPaint;

    private Paint roundPaint;

    private Paint rectPaint;

    private Paint arcPaint;

    private Paint txtPaint;

    /**
     * 绘制
     */
    int min;

    public LabelImageView(Context context) {
        this(context, null);
    }

    public LabelImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    /**
     * 初始化一些自定义的参数
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public LabelImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.LabelImageView, defStyle, 0);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.LabelImageView_label_src:
                    mSrc = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;
                case R.styleable.LabelImageView_label_textsize:
                    mTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 10f, getResources().getDisplayMetrics()));// 默认为10DP
                    break;
                case R.styleable.LabelImageView_label_text:
                    mText = a.getText(attr).toString();
                    break;
            }
        }
        a.recycle();

        init();
    }

    private void init() {
        imgPaint = new Paint();
        roundPaint = new Paint();
        arcPaint = new Paint();
        rectPaint = new Paint();
        txtPaint = new Paint();
    }

    /**
     * 计算控件的高度和宽度
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 设置宽度
         */
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            mWidth = specSize;
        } else {
            // 由图片决定的宽
            int desireByImg = getPaddingLeft() + getPaddingRight() + mSrc.getWidth();
            if (specMode == MeasureSpec.AT_MOST)// wrap_content
            {
                mWidth = Math.min(desireByImg, specSize);
            } else {

                mWidth = desireByImg;
            }
        }

        /***
         * 设置高度
         */

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            mHeight = specSize;
        } else {
            int desire = getPaddingTop() + getPaddingBottom() + mSrc.getHeight();

            if (specMode == MeasureSpec.AT_MOST)// wrap_content
            {
                mHeight = Math.min(desire, specSize);
            } else {
                mHeight = desire;
            }
        }

        min = Math.min(mWidth, mHeight);

        setMeasuredDimension(mWidth, mHeight);
        Log.e("test", "mWidth = " + mWidth + "mHeight = " + mHeight);
    }

    /**
     * refer: http://blog.csdn.net/iispring/article/details/49770651
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //设置画布整体背景色
        canvas.drawARGB(255, 255, 255, 255);
//        canvas.drawBitmap(createCircleImage(mSrc, min), INNER_PADDING, INNER_PADDING, null);
        drawCircleImageDirectly(canvas);
        drawCircle(canvas, min);
        drawRect(canvas, min);
        drawArc(canvas, min);
        drawTextCenter(canvas);
    }

    private void drawTextCenter(Canvas canvas) {
        txtPaint.setTextAlign(Paint.Align.CENTER);
        txtPaint.setFakeBoldText(true);
        txtPaint.setUnderlineText(true);
        canvas.save();
        canvas.translate(min / 2, min / 2);
        txtPaint.setTextSize(mTextSize);
        canvas.drawText("居中文本", 0, 0, txtPaint);
        canvas.restore();
    }

    /**
     * http://blog.csdn.net/iispring/article/details/50472485
     *
     * @param canvas
     */
    private void drawCircleImageDirectly(Canvas canvas) {
        imgPaint.setAntiAlias(true);
        int layerId = canvas.saveLayer(0, 0, min, min, imgPaint, Canvas.ALL_SAVE_FLAG);
        //内部圆直径
        int innerCircle = min - INNER_PADDING * 2;
        //内部圆半径
        int innerCircleRadius = innerCircle / 2;
        //缩放到innerCircle大小
        Bitmap source = Bitmap.createScaledBitmap(mSrc, innerCircle, innerCircle, false);
        canvas.drawCircle(min / 2, min / 2, innerCircleRadius, imgPaint);
        imgPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(source, INNER_PADDING, INNER_PADDING, imgPaint);
        canvas.restoreToCount(layerId);
        imgPaint.setXfermode(null);
    }

    private void drawRect(Canvas canvas, int center) {
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, 0, center, center, rectPaint);
    }

    /**
     * 画右上角圆,右上角圆心在大圆的圆上,给予固定偏移角度,可以算出右上角圆心坐标
     *
     * @param canvas
     * @param center
     */
    private void drawCircle(Canvas canvas, int center) {
        roundPaint.setAntiAlias(true);
        roundPaint.setStyle(Paint.Style.FILL);
        roundPaint.setColor(Color.WHITE);

        int innerCircleRadius = (center - INNER_PADDING * 2) / 2;
        double xCord = center / 2 + innerCircleRadius / Math.sqrt(2);
        double yCord = center / 2 - innerCircleRadius / Math.sqrt(2);

        int radius = (int) (center / 2 - innerCircleRadius / Math.sqrt(2));

        // 画实心圆
        canvas.drawCircle((float) xCord, (float) yCord, radius, roundPaint);

        // 空心圆
        roundPaint.reset();
        roundPaint.setAntiAlias(true);
        roundPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle((float) xCord, (float) yCord, radius, roundPaint);

        /**
         * 获得绘制文本的宽和高
         */
        roundPaint.setTextSize(mTextSize);
        Rect mBound = new Rect();
        roundPaint.getTextBounds(mText, 0, mText.length(), mBound);

        int textSize = mTextSize;
        roundPaint.setTextSize(textSize);
        canvas.drawText(mText, (int) (xCord - mBound.width() / 2), (int) (yCord + mBound.height() / 2), roundPaint);
    }

    /**
     * 画扇形
     *
     * @param canvas
     * @param center
     */
    private void drawArc(Canvas canvas, int center) {
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.STROKE);
        RectF oval = new RectF(0, 0, min, min);
        // startAngle 开始的角度(从右边开始  顺时针转)   passAngle 经过的角度
//        float startAngle = (float)(- (90 - angle / 2 ));
//        float passAngle = 270 - 2 * (float)(- (90 - angle / 2 ));
        float startAngle = 340;
        float passAngle = 310;
        canvas.drawArc(oval, startAngle, passAngle, false, arcPaint);
    }

    /**
     * 根据原图和变长绘制圆形图片 其实是通过新建的canvas 重新构建出一个bitmap
     *
     * @param source
     * @param center
     * @return
     */
    private Bitmap createCircleImage(Bitmap source, int center) {
        imgPaint.setAntiAlias(true);
        //内部圆直径
        int innerCircle = center - INNER_PADDING * 2;
        //内部圆半径
        int innerCircleRadius = innerCircle / 2;
        //缩放到innerCircle大小
        source = Bitmap.createScaledBitmap(source, innerCircle, innerCircle, false);
        //创建和缩放后的Bitmap一样大小的画布
        Bitmap target = Bitmap.createBitmap(innerCircle, innerCircle, Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(innerCircleRadius, innerCircleRadius, innerCircleRadius, imgPaint);
        imgPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, imgPaint);
        imgPaint.setXfermode(null);
        return target;

    }

    public void setTxt(String s) {
        this.mText = s;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mSrc = bm;
        invalidate();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        mSrc = getBitmapFromDrawable(drawable);
        invalidate();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        mSrc = getBitmapFromDrawable(getDrawable());
        invalidate();
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLOR_DRAWABLE_DIMENSION, COLOR_DRAWABLE_DIMENSION,
                        BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }
}
