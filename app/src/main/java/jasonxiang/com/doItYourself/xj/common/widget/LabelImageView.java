package jasonxiang.com.doItYourself.xj.common.widget;

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

    public static final String TAG = LabelImageView.class.getSimpleName();

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

    /**
     * 右上角圆圈
     */
    private Paint roundPaint;

    private Paint rectPaint;

    private Paint arcPaint;

    private Paint txtPaint;

    private Paint pointPaint;

    /**
     * 绘制
     */
    int min;

    public LabelImageView(Context context) {
        this(context, null);
        init();
    }

    public LabelImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
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
        mSrc = BitmapFactory.decodeResource(getResources(), a.getResourceId(R.styleable.LabelImageView_label_src, R.drawable.pet));
        mTextSize = a.getDimensionPixelSize(R.styleable.LabelImageView_label_textsize, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 200f, getResources().getDisplayMetrics()));// 默认为10DP
        mText = a.getText(R.styleable.LabelImageView_label_text).toString();
        a.recycle();

        init();
    }

    private void init() {
//        if(Build.VERSION.SDK_INT >= 11){
//            setLayerType(LAYER_TYPE_SOFTWARE, null);
//        }
        imgPaint = new Paint();
        imgPaint.setAntiAlias(true);

        roundPaint = new Paint();
        roundPaint.setAntiAlias(true);
        roundPaint.setStyle(Paint.Style.FILL);
        roundPaint.setColor(Color.RED);

        arcPaint = new Paint();
        rectPaint = new Paint();
        txtPaint = new Paint();

        pointPaint = new Paint();
        pointPaint.setColor(Color.BLACK);
        pointPaint.setStrokeWidth(10.0f);
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
        Log.e(TAG, "mWidth = " + mWidth + "mHeight = " + mHeight);
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
        //canvas.drawBitmap(createCircleImage(mSrc, min), INNER_PADDING, INNER_PADDING, null);
        drawCircleImageDirectly(canvas);
        drawRightTopCircle(canvas, min);
        drawRect(canvas, min);
        drawArc(canvas, min);
        drawTextCenter(canvas);
        drawCenterPoint(canvas);
    }

    private void drawCenterPoint(Canvas canvas){
        canvas.drawPoint(min / 2, min/ 2, pointPaint);
    }

    private void drawTextCenter(Canvas canvas) {
        txtPaint.setTextAlign(Paint.Align.CENTER);
        txtPaint.setFakeBoldText(true);
        txtPaint.setUnderlineText(true);
        canvas.save();
        canvas.translate(min / 2, min / 2);
        txtPaint.setTextSize(mTextSize);
        Rect rect = new Rect();
        String text = "居中文本";
        txtPaint.getTextBounds(text, 0, text.length(), rect);
        canvas.drawText(text, 0, rect.height() / 2, txtPaint);
        canvas.restore();
    }

    /**
     * http://blog.csdn.net/iispring/article/details/50472485
     *
     * @param canvas
     */
    private void drawCircleImageDirectly(Canvas canvas) {
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
    private void drawRightTopCircle(Canvas canvas, int center) {
        int innerCircleRadius = (center - INNER_PADDING * 2) / 2;
        double xCord = center / 2 + innerCircleRadius / Math.sqrt(2);
        double yCord = center / 2 - innerCircleRadius / Math.sqrt(2);

        int radius = (int) (center / 2 - innerCircleRadius / Math.sqrt(2));

        // 画实心圆
        canvas.drawCircle((float) xCord, (float) yCord, radius, roundPaint);

        // 外部实线空心圆
        roundPaint.reset();
        roundPaint.setAntiAlias(true);
        roundPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle((float) xCord, (float) yCord, radius, roundPaint);

        /**
         * 绘制右上角圆形内的文本描述
         */
        roundPaint.setTextSize(mTextSize);
        Rect mBound = new Rect();
        roundPaint.getTextBounds(mText, 0, mText.length(), mBound);
        canvas.drawText(mText, (int) (xCord - mBound.width() / 2),
                (int) (yCord + mBound.height() / 2), roundPaint);
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
