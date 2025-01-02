package com.serenegiant.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerSurfaceView extends SurfaceView implements SurfaceHolder.Callback, AspectRatioViewInterface {
//public class PlayerSurfaceView extends SurfaceView implements SurfaceHolder.Callback, AspectRatioViewInterface, Runnable {
    //声明SurfaceHolder对象
    private SurfaceHolder mHolder;
    //声明子线程对象
    private Thread mThread;
    //声明画笔对象
    private Paint mPaint;
    //声明画布对象
    private Canvas mCanvas;
    //声明一个标志位，用于控制子线程的退出
    private boolean mIsDrawing;

    private double mRequestedAspect = -1.0;

    //构造方法，初始化相关对象
    public PlayerSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public PlayerSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public PlayerSurfaceView(Context context) {
        super(context);
        init();
    }

    private void init() {
        //获取SurfaceHolder对象
        ////mHolder = getHolder();
        //注册SurfaceHolder的回调方法
        ////mHolder.addCallback(this);
        //初始化画笔对象，设置颜色和宽度
        ////mPaint = new Paint();
        ////mPaint.setColor(Color.RED);
        ////mPaint.setStrokeWidth(10);
    }

    //当Surface被创建时，启动子线程，并根据需要调整View的大小或位置
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

        //设置标志位为true，表示子线程可以开始运行
        ////mIsDrawing = true;
        //创建并启动子线程
        ////mThread = new Thread(this);
        ////mThread.start();
    }

    //当Surface被改变时，重新获取Surface的宽高，并根据需要调整View的大小或位置
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        //TODO: 根据需要调整View的大小或位置
    }

    //当Surface被销毁时，停止子线程，并释放相关资源
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        //设置标志位为false，表示子线程可以停止运行
        ////mIsDrawing = false;
        ////try {
        ////    //等待子线程结束，并释放子线程对象
        ////    mThread.join();
        ////    mThread = null;
        ////} catch (InterruptedException e) {
        ////    e.printStackTrace();
        ////}
    }

    /**
     * measure view size with keeping aspect ratio
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (mRequestedAspect > 0) {
            int initialWidth = MeasureSpec.getSize(widthMeasureSpec);
            int initialHeight = MeasureSpec.getSize(heightMeasureSpec);

            final int horizPadding = getPaddingLeft() + getPaddingRight();
            final int vertPadding = getPaddingTop() + getPaddingBottom();
            initialWidth -= horizPadding;
            initialHeight -= vertPadding;

            final double viewAspectRatio = (double)initialWidth / initialHeight;
            final double aspectDiff = mRequestedAspect / viewAspectRatio - 1;

            // stay size if the difference of calculated aspect ratio is small enough from specific value
            if (Math.abs(aspectDiff) > 0.01) {
                if (aspectDiff > 0) {
                    // adjust height from width
                    initialHeight = (int) (initialWidth / mRequestedAspect);
                } else {
                    // adjust width from height
                    initialWidth = (int) (initialHeight * mRequestedAspect);
                }
                initialWidth += horizPadding;
                initialHeight += vertPadding;
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(initialWidth, MeasureSpec.EXACTLY);
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(initialHeight, MeasureSpec.EXACTLY);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                super.setDesiredHdrHeadroom(2.0F);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void onPause() {
    }

    public void onResume() {
    }

    /**
     * set aspect ratio of this view
     * <code>aspect ratio = width / height</code>.
     */
    public void setAspectRatio(double aspectRatio) {
        if (aspectRatio < 0) {
            throw new IllegalArgumentException();
        }
        if (mRequestedAspect != aspectRatio) {
            mRequestedAspect = aspectRatio;
            requestLayout();
        }
    }

}
