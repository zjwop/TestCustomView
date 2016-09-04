package com.example.zhaojw.testcustomview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.example.zhaojw.testcustomview.R;

/**
 * Created by zjw on 2016/9/5.
 */
public class CircleView extends View {

    private Context mContext;
    private int width;
    private int height;
    private Canvas mCanvas;
    private Paint mPaint;

    private Bitmap shadowLayer;

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mPaint = new Paint();
        post(new Runnable() {
            @Override
            public void run() {
                initViewWidthAndHeight();
                setShadowLayer();
            }
        });
    }

    private void initViewWidthAndHeight(){
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    private void setShadowLayer(){
        shadowLayer = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(shadowLayer);
        mCanvas.drawColor(getResources().getColor(R.color.colorPrimary));

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mPaint.setAlpha(0);
        mCanvas.drawCircle(width/2, height/2, Math.min(width, height)/2, mPaint);
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

    private Bitmap drawableToBitmap(Drawable drawable) // drawable 转换成bitmap
    {
        int width = drawable.getIntrinsicWidth();// 取drawable的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ?Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565;// 取drawable的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);// 建立对应bitmap
        Canvas canvas = new Canvas(bitmap);// 建立对应bitmap的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);// 把drawable内容画到画布中
        return bitmap;
    }
}
