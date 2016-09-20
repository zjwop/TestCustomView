package com.example.zhaojw.testcustomview.view;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.example.zhaojw.testcustomview.R;

/**
 * Created by zhaojw on 2016/8/18.
 */
public class SlidingMenuLayout extends LinearLayout{

    private Context mContext;
    private FrameLayout menuContainer;
    private FrameLayout container;

    private int menuWidth;

    private Scroller scroller;
    private VelocityTracker velocityTracker;
    private float beginX;
    private float curX;
    private float endX;

    private float startX;
    private float startY;

    private static final int SNAP_VELOCITY = 200;

    public SlidingMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    void initView(){
        scroller = new Scroller(mContext);
        setOrientation(LinearLayout.HORIZONTAL);

        Point size = new Point();
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getSize(size);
        menuWidth = size.x / 2;

        menuContainer = new FrameLayout(mContext);
        menuContainer.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        LayoutParams menuLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        menuLayoutParams.width = menuWidth;
        menuContainer.setLayoutParams(menuLayoutParams);
        addView(menuContainer);

        container = new FrameLayout(mContext);
        container.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        LayoutParams containerLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        containerLayoutParams.width = size.x;
        container.setLayoutParams(containerLayoutParams);
        addView(container);

        scrollTo(menuWidth, 0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        boolean isIntercept = false;
        float currentX = 0;
        float currentY = 0;
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = ev.getRawX();
                startY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = ev.getRawX();
                currentY = ev.getRawY();

                if(Math.abs(currentX - startX) >= Math.abs(currentY - startY)){
                    isIntercept = true;
                }else{
                    isIntercept = false;
                }
                startX = currentX;
                startY = currentY;
                break;
            case MotionEvent.ACTION_UP:
                isIntercept = false;
                break;
        }

        return isIntercept;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(velocityTracker == null){
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);

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
                    if(destScrollX > menuWidth){
                        destScrollX = menuWidth;
                    }
                }
                scrollTo(destScrollX, 0);
                beginX = curX;
                break;
            case MotionEvent.ACTION_UP:
                endX = event.getRawX();
                velocityTracker.computeCurrentVelocity(1000);
                if(velocityTracker.getXVelocity() >= SNAP_VELOCITY){
                    scrollToShowMenu(scrollX);
                }else if(velocityTracker.getXVelocity() <= -SNAP_VELOCITY){
                    scrollToHideMenu(scrollX);
                }else if(scrollX >= menuWidth/2){
                    scrollToHideMenu(scrollX);
                }else{
                    scrollToShowMenu(scrollX);
                }
                velocityTracker.recycle();
                velocityTracker = null;
                break;
        }

        return true;
    }

    private void scrollToShowMenu(int scrollX){
        scroller.startScroll(scrollX, 0, -scrollX, 0);
        invalidate();

    }

    private void scrollToHideMenu(int scrollX){
        scroller.startScroll(scrollX, 0, menuWidth-scrollX, 0);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }

    }

    public View getMenuContainer(){
        return menuContainer;
    }

    public View getContainer(){
        return container;
    }
}
