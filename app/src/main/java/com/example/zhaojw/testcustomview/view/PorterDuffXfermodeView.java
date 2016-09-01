package com.example.zhaojw.testcustomview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zjw on 2016/9/1.
 */
public class PorterDuffXfermodeView extends View{

    Context mContext;
    int width;
    int height;
    Canvas mCanvas;
    Paint mPaint;
    Path mPath;

    Bitmap shadowLayer;

    public PorterDuffXfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mPaint = new Paint();
        mPath = new Path();
        post(new Runnable() {
            @Override
            public void run() {
                initViewWidthAndHeight();
                setHintLayer();
            }
        });
    }

    private void initViewWidthAndHeight(){
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    private void setHintLayer(){
        shadowLayer = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(shadowLayer);
        mCanvas.drawColor(Color.GRAY);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        mPaint.setAlpha(0);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(100);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mCanvas.drawPath(mPath, mPaint);
        invalidate();
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = 0;
        int measureHeight = 0;
        if(MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST){
            measureWidth = getBackground().getIntrinsicWidth();
        }else{
            measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        }
        if(MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST){
            measureHeight = getBackground().getIntrinsicWidth();
        }else{
            measureHeight = MeasureSpec.getSize(widthMeasureSpec);
        }
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(shadowLayer, 0, 0, null);
    }
}
