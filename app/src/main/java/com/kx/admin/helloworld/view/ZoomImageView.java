package com.kx.admin.helloworld.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;


public class ZoomImageView extends android.support.v7.widget.AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener, ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {
    private boolean isOnce = false;
    private ScaleGestureDetector scaleGestureDetector;
    Matrix matrix;
    /**
     * 初始的缩放比例
     */
    private float mInitScale;
    /**
     * 最大缩放比例
     */
    private float mMaxScale;
    /**
     * 中间缩放比例值
     */
    private float mMidScale;
    // 缩放比例在  mInitScale ----     mMaxScale  之间

    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        matrix = new Matrix();
        scaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        if (!isOnce) {
            Drawable drawable = getDrawable();
            if (drawable != null) {
                //空间宽和高
                int width = getWidth();
                int height = getHeight();
                //图片宽和高
                int dw = drawable.getIntrinsicWidth();
                int dh = drawable.getIntrinsicHeight();
                System.out.println(width);
                System.out.println(dw);
                float scale = 1.0f;
                // 图片宽度大于 空间宽度，  图片高度小于空间高度      （根据宽度缩放）
                if (dw > width && dh < height) {
                    scale = width * 1.0f / dw;
                }
                // 图片宽度小于 空间宽度，  图片高度大于空间高度      （根据高度缩放）
                if (dw < width && dh > height) {
                    scale = height * 1.0f / dh;
                }
                // 图片宽高均大于控件宽高      图片宽高均小于控件宽高
                if ((dw > width && dh > height) || (dw < width && dh < height)) {
                    scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);   //  如果取较大的一个，会造成显示不全的问题,scale * dw   或者 scale *  dh  会大于控件的宽或高
                }
                mInitScale = scale;
                mMaxScale = scale * 4;
                mMidScale = scale * 2;
                int dx = getWidth() / 2 - dw / 2;
                int dy = getHeight() / 2 - dh / 2;
                matrix.postTranslate(dx, dy);
                matrix.postScale(scale, scale, width / 2, height / 2);
                setImageMatrix(matrix);
            }
            isOnce = true;
        }
    }

    /**
     * 获取当前图片的缩放值
     *
     * @return
     */
    private float getScale() {
        float[] values = new float[9];
        matrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scaleFactor = detector.getScaleFactor();
        //计算最新的图片缩放值
        float currentScale = getScale();
        if (getDrawable() == null) {
            return true;
        }
        //     想缩小                                                      //想放大
        if ((currentScale<mMaxScale && scaleFactor <1 )||  (currentScale>mInitScale && scaleFactor >1 )){
            if (scaleFactor* currentScale< mInitScale){  //  当图片缩放比例小于最小值时，将缩放比例设置为最小值
                scaleFactor = mInitScale/currentScale;
            }
            if (scaleFactor* currentScale >mMaxScale){  //  当图片缩放比例大于最大值时，将缩放比例设置为最大值
                scaleFactor = mMaxScale/currentScale;
            }
        }
            matrix.postScale(scaleFactor, scaleFactor, getWidth() / 2, getHeight() / 2);
        setImageMatrix(matrix);
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {

        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector)

    {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }
}
