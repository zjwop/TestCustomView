package com.example.zhaojw.testcustomview.view;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.zhaojw.testcustomview.R;
import com.example.zhaojw.testcustomview.util.DisplayUtil;

import java.util.ArrayList;

/**
 * Created by zjw on 2016/8/28.
 */
public class TableGridLayout extends ViewGroup{

    private Context mContext;

    private static int OUT_ITEMS_MARGIN = 12;
    private static int INTER_ITEMS_MARGIN = 10;
    private static int MAX_SHOW_LINES = 3;

    private int windowWidth;
    private int outItemsMargin;
    private int interItemsMargin;

    private ArrayList<String> datas;
    private static String[] strings = {"zhaojw","one piece","javen","helloword"};

    private Scroller mScroller;
    private boolean isScroll;
    private boolean isAddView;
    private boolean isDeleteView;

    private int contentHeight;
    private int widgetHeight;
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

        outItemsMargin = DisplayUtil.dp2px(mContext, OUT_ITEMS_MARGIN);
        interItemsMargin = DisplayUtil.dp2px(mContext, INTER_ITEMS_MARGIN);

        datas = new ArrayList<String>();

        mScroller = new Scroller(mContext);

        initChildViews();
    }


    public void addChild(String string){
        datas.add(string);
        View view = getChildView(string);
        addView(view);
        isAddView = true;
    }



    private void initChildViews(){
        for(int i = 0; i < strings.length; i++){
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
        textView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        textView.setOnClickListener(mDeleteItemListner);
        return textView;
    }

    //设置删除回调,并设定动画
    private View.OnClickListener mDeleteItemListner = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            //Animation scaleAnimation = AnimationUtils.loadAnimation(mContext, R.anim.table_grid_item_delete);
            final View view = v;
            ScaleAnimation scaleAnimation = new ScaleAnimation(1,0,1,0);
            scaleAnimation.setDuration(1000);
            view.startAnimation(scaleAnimation);
            scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    removeView(view);
                    isDeleteView = true;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }
    };


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        int rowNum = 0;
        int rowRemain = windowWidth - outItemsMargin * 2;
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
                    contentHeight = outItemsMargin * 2 + interItemsMargin*(rowNum-1) + childHeight * rowNum;
                    widgetHeight = contentHeight;
                    setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), widgetHeight);
                    isScroll = false;
                }else{
                    contentHeight = outItemsMargin * 2 + interItemsMargin * ( rowNum - 1 ) + childHeight * rowNum;
                    widgetHeight = outItemsMargin * 2 + interItemsMargin * (MAX_SHOW_LINES - 1)  + childHeight * MAX_SHOW_LINES;
                    setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), widgetHeight);
                    maxScrollY = contentHeight -  widgetHeight;
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
        //设置添加和删除子View时候正确的滚动位置
        if(isAddView == true){
            isAddView = false;
            scrollToBottom();
        }
        if(isDeleteView == true){
            isAddView = false;
            if(!isScroll){
                scrollToTop();
            }else{
                if(contentHeight - getScrollY() <= widgetHeight){
                    scrollToBottom();
                }else{
                    return;
                }
            }

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


    private void scrollToBottom(){
        int scrollY = getScrollY();
        int deltaY = maxScrollY - scrollY;
        mScroller.startScroll(0,scrollY,0,deltaY,500);
        postInvalidate();
    }

    private void scrollToTop(){
        int scrollY = getScrollY();
        int deltaY = 0 - scrollY;
        mScroller.startScroll(0,scrollY,0,deltaY,500);
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
