package com.example.zhaojw.testcustomview.view;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;

import com.example.zhaojw.testcustomview.R;

import java.util.ArrayList;

/**
 * Created by zhaojw on 2016/8/18.
 */
public class PullToRefreshLayout extends LinearLayout{

    private Context mContext;
    private FrameLayout header;
    private ListView listView;

    private int headerHeight;

    private Scroller scroller;
    private VelocityTracker velocityTracker;
    private float startY;
    private float curY;
    private float endY;


    private boolean isToTop;
    private boolean isShowHeader;

    private static final int SNAP_VELOCITY = 200;

    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        isToTop = true;
        isShowHeader = false;
    }


    void initView(){
        scroller = new Scroller(mContext);
        setOrientation(LinearLayout.VERTICAL);

        Point size = new Point();
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getSize(size);
        headerHeight = size.y / 3;

        header = new FrameLayout(mContext);
        header.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        LayoutParams headerLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        headerLayoutParams.height = headerHeight;
        header.setLayoutParams(headerLayoutParams);
        addView(header);

        listView = new ListView(mContext);
        listView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        LayoutParams listViewLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        listViewLayoutParams.height = size.y;
        listView.setLayoutParams(listViewLayoutParams);
        addView(listView);

        ArrayList<String> datas = new ArrayList<String>();
        for(int i = 0; i < 30; i++){
            datas.add("test item id "+ i);
        }
        ArrayAdapter<String> adatper = new ArrayAdapter<String>(mContext,R.layout.list_item,R.id.list_text,datas);
        listView.setAdapter(adatper);

        listView.setSelection(0);

        scrollTo(0, headerHeight);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                //判断是否到顶
                if(firstVisibleItem == 0){
                    View firstView = view.getChildAt(0);
                    if(firstView != null){
                        int top = firstView.getTop();
                        if(top >= 0){
                            isToTop = true;
                        }else{
                            isToTop = false;
                        }
                    }

                }
                else{
                    isToTop = false;
                }

            }
        });

        listView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        curY = event.getRawY();
                        if(curY - startY >= 0){
                            if(isToTop == true){
                                isShowHeader = true;
                            }
                        }else{
                            isShowHeader = false;
                        }
                        startY = curY;
                        break;
                    case MotionEvent.ACTION_UP:
                        isShowHeader = false;
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        boolean isIntercept = false;
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                isIntercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if(isShowHeader){
                    isIntercept = true;
                }else{
                    isIntercept = false;
                }
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

        int scrollY = getScrollY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                curY = event.getRawY();
                int distanceY = (int) (curY - startY);
                int destScrollY = 0;
                if(distanceY >= 0){
                    destScrollY = scrollY - distanceY;
                    if(destScrollY < 0){
                        destScrollY = 0;
                    }

                }else
                {
                    destScrollY = scrollY + (-distanceY);
                    if(destScrollY > headerHeight){
                        destScrollY = headerHeight;
                    }
                }
                scrollTo(0, destScrollY);
                startY = curY;
                break;
            case MotionEvent.ACTION_UP:
                endY = event.getRawY();
                scrollToHideHeader(scrollY);
                velocityTracker.computeCurrentVelocity(1000);
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

    private void scrollToHideHeader(int scrollY){
        scroller.startScroll(0, scrollY, 0, headerHeight-scrollY);
        isShowHeader = false;
        isToTop = true;
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

}
