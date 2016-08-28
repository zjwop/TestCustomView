package com.example.zhaojw.testcustomview.view;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhaojw.testcustomview.R;

import java.util.ArrayList;

/**
 * Created by zjw on 2016/8/28.
 */
public class TableGridLayout extends ViewGroup{

    private Context mContext;

    private static int OUT_ITEMS_MARGIN = 30;
    private static int INTER_ITEMS_MARGIN = 20;
    private static int MAX_SHOW_LINES = 3;

    private int windowWidth;
    private int outItemsMargin;
    private int interItemsMargin;

    private ArrayList<String> datas;
    private static String[] strings = {"0abc","1abcd","2ab","3abcdefg",
                                        "4ab","5abcdefhigklac","6adce","7adbefsc"};
    private boolean isScroll;
    private int maxScrollY;

    private float beginY;
    private float curY;
    private float endY;

    public TableGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        Point size = new Point();
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getSize(size);
        windowWidth = size.x;
        //TODO add dp2px;
        outItemsMargin = OUT_ITEMS_MARGIN;
        interItemsMargin = INTER_ITEMS_MARGIN;
        datas = new ArrayList<String>();

        initChildViews();
    }


    public void addChild(String string){
        datas.add(string);
        datas.add(string);
        View view = getChildView(string);
        addView(view);
        requestLayout();
    }



    private void initChildViews(){
        for(int i = 0; i <8; i++){
            String string = strings[i];
            datas.add(string);
            View view = getChildView(string);
            addView(view);
        }
    }

    private View getChildView(String string){
        TextView textView = new TextView(mContext);
        MarginLayoutParams layoutParams = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);
        textView.setText(string);
        textView.setTextSize(40);
        textView.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        return textView;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        int rowNum = 0;
        int rowRemain = windowWidth ;
        if(count > 0){
            rowNum = 1;
            for(int i=0; i < count; i++){
                View childView = getChildAt(i);
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);

                int childWidth = childView.getMeasuredWidth();
                int childHeight = childView.getMeasuredHeight();
                if(childWidth <= rowRemain){
                    rowRemain = rowRemain - childWidth - interItemsMargin;
                }else{
                    rowNum++;
                    rowRemain = windowWidth - outItemsMargin * 2 - childWidth;
                }

                if(rowNum <= MAX_SHOW_LINES){
                    int totalHeight = outItemsMargin * 2 + interItemsMargin*(rowNum-1) + childHeight * rowNum;
                    setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), totalHeight);
                    isScroll = false;
                }else{
                    int totalHeight = outItemsMargin * 2 + interItemsMargin*(rowNum-1) + childHeight * rowNum;
                    int viewHeight = outItemsMargin + interItemsMargin * MAX_SHOW_LINES + childHeight * MAX_SHOW_LINES;
                    setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), viewHeight);
                    maxScrollY = totalHeight -  viewHeight;
                    isScroll = true;
                }


            }
        }else{
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), 0);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        if(count == 0){
            return;
        }
        int topBegin = outItemsMargin;
        int leftBegin = outItemsMargin;
        int rowRemain = windowWidth - outItemsMargin * 2;
        for(int i=0; i < count; i++){
            View childView = getChildAt(i);
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            if(childWidth <= rowRemain){
                childView.layout(leftBegin, topBegin, leftBegin + childWidth, topBegin + childHeight);
            }
            else{
                topBegin = topBegin + childHeight + interItemsMargin;
                leftBegin = outItemsMargin;
                rowRemain = windowWidth - interItemsMargin * 2;
                childView.layout(leftBegin, topBegin, leftBegin + childWidth, topBegin + childHeight);

            }
            leftBegin = leftBegin + childWidth + interItemsMargin;
            rowRemain = rowRemain - childWidth - interItemsMargin;

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isScroll){
            int scrollY = getScrollY();
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    beginY = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    curY = event.getRawY();
                    float deltaY = curY - beginY;
                    if(deltaY >= 0){
                        int destY = (int)(scrollY - deltaY);
                        if(destY < 0){
                            destY = 0;
                        }
                        scrollTo(0, destY);
                    }else{
                        int destY = (int)(scrollY + (-deltaY));
                        if( destY > maxScrollY ){
                            destY = maxScrollY;
                        }
                        scrollTo(0, destY);
                    }
                    beginY = curY;
                    break;
                case MotionEvent.ACTION_UP:
                    endY = event.getRawY();
            }
            return true;
        }
        return super.onTouchEvent(event);
    }
}
