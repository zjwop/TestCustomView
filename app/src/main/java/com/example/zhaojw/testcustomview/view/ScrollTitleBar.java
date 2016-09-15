package com.example.zhaojw.testcustomview.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.zhaojw.testcustomview.R;
import com.example.zhaojw.testcustomview.util.DisplayUtil;

import java.util.ArrayList;

/**
 * Created by zjw on 2016/9/15.
 */
public class ScrollTitleBar extends ViewGroup{

    private static int TOP_MARGIN_DP = 10;
    private static int BEGIN_END_MARGIN_DP = 15;
    private static int INTER_ITEM_MARGIN_DP = 20;

    public Context mContext;
    public ArrayList<String> titleNameList = new ArrayList<>();


    private int contentWidth;
    private int windowWidth;

    private int widgetWidth;
    private int widgetHeight;

    private int topMargin;
    private int beginEndMargin;
    private int interItemMargin;

    private Scroller scroller;
    private float beginX;
    private float curX;
    private float endX;

    private ItemSelectedListener mItemSelectedListener;

    public ScrollTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        scroller = new Scroller(mContext);

        topMargin = DisplayUtil.dp2px(mContext, TOP_MARGIN_DP);
        beginEndMargin = DisplayUtil.dp2px(mContext, BEGIN_END_MARGIN_DP);
        interItemMargin = DisplayUtil.dp2px(mContext, INTER_ITEM_MARGIN_DP);

        Point size = new Point();
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getSize(size);
        windowWidth = size.x;
        widgetWidth = windowWidth;
    }

    public void setDataList(ArrayList<String> titleNameList){
        this.titleNameList = titleNameList;
        initView();
    }

    public void initView(){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        for(int i = 0; i < titleNameList.size(); i++){
            View view = inflater.inflate(R.layout.scroll_title_bar_item, null);
            TextView textView = (TextView)view.findViewById(R.id.scroll_bar_item_text);
            textView.setText(titleNameList.get(i));
            addView(view);
            textView.setOnClickListener(new OnItemClickListener(i, mItemSelectedListener));
        }
        setIndicatorState(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        contentWidth = beginEndMargin * 2;
        for(int i=0; i < count; i++){
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            int childWidth = childView.getMeasuredWidth();
            if(i == 0 ){
                widgetHeight = childView.getMeasuredHeight() + topMargin;
            }

            contentWidth += childWidth;
            if(i != count - 1){
                contentWidth += interItemMargin;
            }

        }
        setMeasuredDimension(widgetWidth, widgetHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        if(count == 0){
            return;
        }
        int topBegin = topMargin;
        int leftBegin = beginEndMargin;
        for(int i=0; i < count; i++){
            View childView = getChildAt(i);
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            childView.layout(leftBegin, topBegin, leftBegin + childWidth, topBegin + childHeight);

            leftBegin = leftBegin + childWidth + interItemMargin;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int scrollX = getScrollX();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                beginX = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                curX = event.getRawX();
                int distanceX = (int) (curX - beginX);
                int destScrollX = 0;
                if(distanceX >= 0){
                    destScrollX = scrollX - distanceX;
                    if(destScrollX < 0){
                        destScrollX = 0;
                    }

                }else
                {
                    destScrollX = scrollX + (-distanceX);
                    if(destScrollX > contentWidth - widgetWidth){
                        destScrollX = contentWidth - widgetWidth;
                    }
                }
                scrollTo(destScrollX, 0);
                beginX = curX;
                break;
            case MotionEvent.ACTION_UP:
                endX = event.getRawX();
                break;
        }

        return true;
    }

    private int getChildXMiddleAt(int index){
        View view = getChildAt(index);
        int left = view.getLeft() - getScrollX();
        int right = view.getRight() - getScrollX();
        int middle = (left + right)/2;
        return middle;
    }

    public void selectItemAt(int index){
        int itemMiddle = getChildXMiddleAt(index);
        int windowMiddle = windowWidth / 2;
        int distanceX = itemMiddle - windowMiddle;
        int destScrollX = 0;
        int scrollX = getScrollX();
        destScrollX = scrollX + distanceX;
        if(destScrollX > contentWidth - widgetWidth){
            destScrollX = contentWidth - widgetWidth;
        }else if(destScrollX < 0){
            destScrollX = 0;
        }

        setIndicatorState(index);

        scroller.startScroll(scrollX,0,destScrollX-scrollX,0);
        invalidate();

    }

    public void setIndicatorState(int index){
        for(int i = 0; i < titleNameList.size(); i++){
            TextView textView = (TextView)getChildAt(i).findViewById(R.id.scroll_bar_item_text);
            View indicator = getChildAt(i).findViewById(R.id.scroll_bar_item_indicator);
            if( i == index){
                textView.setTextColor(Color.BLACK);
                indicator.setVisibility(VISIBLE);
            }else{
                textView.setTextColor(Color.GRAY);
                indicator.setVisibility(INVISIBLE);
            }
        }
    }



    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }

    }

    private class OnItemClickListener implements View.OnClickListener{
        private int index;
        private ItemSelectedListener mItemSelectedListener;
        public OnItemClickListener(int index, ItemSelectedListener itemSelectedListener ) {
            this.index = index;
            mItemSelectedListener = itemSelectedListener;
        }
        @Override
        public void onClick(View v) {
            mItemSelectedListener.onItemSelected(index);
        }
    }

    public interface ItemSelectedListener{
        void onItemSelected(int index);
    }

    public void setItemSelectedListener(ItemSelectedListener itemSelectedListener){
        this.mItemSelectedListener = itemSelectedListener;
    }


}
