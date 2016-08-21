package com.example.zhaojw.testcustomview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.zhaojw.testcustomview.R;

/**
 * Created by zhaojw on 2016/7/21.
 */
public class SlidingDeleteLayout extends LinearLayout {


    private Context mContext;
    private int screenWidth;
    private int deleteViewWidth;

    private TextView iconTextView;
    private TextView iconButton;
    private TextView showListButton;
    private Button deleteButton;

    private RelativeLayout contentView;
    private RelativeLayout deleteView;
    private LayoutParams contentLayoutParams;
    private LayoutParams deleteLayoutParams;

    private FrameLayout iconContainer;
    private FrameLayout contentContainer;
    private FrameLayout showListButtonContainer;

    private View contentImp;
    private int contentImpResId;

    private boolean isDeleteViewShow;
    private boolean isContentListShow;

    private Scroller scroller;
    private VelocityTracker velocityTracker;

    private float beginX;
    private float curX;
    private float endX;

    private SlidingDeleteLayout.ContentShowListener contentShowListener;
    private SlidingDeleteLayout.DeleteItemListener deleteItemListener;

    public static final int VIEW_BASE_WIDTH_DP = 45;

    public SlidingDeleteLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if(attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlidingDeleteLayout);
            if (a != null) {

                contentImpResId = a.getResourceId(R.styleable.SlidingDeleteLayout_content_layout, 0);

                LayoutInflater infalter = LayoutInflater.from(mContext);
                contentImp = infalter.inflate(this.contentImpResId, null);
                a.recycle();
            }
        }

        initWidget();
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);

    }

    private void initWidget(){

        scroller = new Scroller(mContext);
        //加载内容标题区域视图
        contentView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.slidingdelete_content, null);
        addView(contentView);
        //加载删除区域视图
        deleteView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.slidingdelete_delete, null);
        addView(deleteView);

        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        windowManager.getDefaultDisplay().getSize(size);
        screenWidth = size.x;
        //DeviceInfoUtil.getPixelFromDip()
        deleteViewWidth = dip2px(mContext, VIEW_BASE_WIDTH_DP);

        contentLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        deleteLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        contentLayoutParams.width = screenWidth;
        contentLayoutParams.leftMargin = 0;
        deleteLayoutParams.width = deleteViewWidth;
        contentView.setLayoutParams(contentLayoutParams);
        deleteView.setLayoutParams(deleteLayoutParams);

        iconTextView = (TextView)contentView.findViewById(R.id.tv_icon_close);
        iconButton = (TextView)contentView.findViewById(R.id.btn_icon_open);
        showListButton = (TextView) contentView.findViewById(R.id.btn_content_list);
        deleteButton = (Button) deleteView.findViewById(R.id.btn_remove);

        contentContainer = (FrameLayout) contentView.findViewById(R.id.content_container_layout);
        if(contentImp != null){
            contentContainer.addView(contentImp);
            contentContainer.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v) {

                    if(isDeleteViewShow)
                    {
                        int scrollX = getScrollX();
                        scrollToHideDeleteView(scrollX);
                    }
                }
            });
        }

        iconContainer = (FrameLayout)contentView.findViewById(R.id.content_icon_layout);
        showListButtonContainer = (FrameLayout)contentView.findViewById(R.id.content_list_button_layout);

        iconContainer.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if (iconButton.getVisibility() == View.VISIBLE)
                {
                    if(scroller.isFinished())
                    {
                        if(!isDeleteViewShow){
                            int scrollX = getScrollX();
                            scrollToShowDeleteView(scrollX);
                            invalidate();
                            isDeleteViewShow = true;
                        }
                    }
                }

            }
        });


        showListButtonContainer.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if(isDeleteViewShow)
                {
                    int scrollX = getScrollX();
                    scrollToHideDeleteView(scrollX);
                }
                else
                {
                    if(isContentListShow == false){
                        iconTextView.setVisibility(View.INVISIBLE);
                        iconButton.setVisibility(View.VISIBLE);
                        showListButton.setBackgroundResource(R.drawable.arrow_down);
                        if(contentShowListener != null){
                            contentShowListener.onContentShow();
                        }
                        isContentListShow = true;
                    }else{
                        iconTextView.setVisibility(View.VISIBLE);
                        iconButton.setVisibility(View.INVISIBLE);
                        showListButton.setBackgroundResource(R.drawable.arrow_up);
                        if(contentShowListener != null){
                            contentShowListener.onContentClose();
                        }
                        isContentListShow = false;
                    }
                }


            }
        });


        deleteButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(scroller.isFinished())
                {
                    if(isDeleteViewShow){
                        int scrollX = getScrollX();
                        scrollToHideDeleteView(scrollX);
                        invalidate();
                        if(deleteItemListener!=null)
                        {
                            deleteItemListener.onDeleteItem();
                        }
                        isDeleteViewShow = false;
                    }
                }
            }
        });
    }

    public void scrollToHideDeleteView(int scrollX){
        scroller.startScroll(scrollX,0,-scrollX,0);
        invalidate();
        isDeleteViewShow = false;
    }

    public void scrollToShowDeleteView(int scrollX){
        scroller.startScroll(scrollX,0,deleteViewWidth-scrollX,0);
        invalidate();
        isDeleteViewShow = true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }

    }



    public void setIconText(String string){
        iconTextView.setText(string);
    }

    public void setContentShowListener(SlidingDeleteLayout.ContentShowListener contentShowListener)
    {
        this.contentShowListener = contentShowListener;
    }

    public void setDeleteItemListener(SlidingDeleteLayout.DeleteItemListener contentShowListener)
    {
        this.deleteItemListener = deleteItemListener;
    }

    //内容列表回调接口
    public interface ContentShowListener{
        public void onContentShow();
        public void onContentClose();
    }
    //删除按钮回调接口
    public interface DeleteItemListener{
        public void onDeleteItem();
    }

    //如果需要拖动滑动，则取消给部分注释
    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isDeleteViewShow)
        {
            int scrollX = getScrollX();
            switch (event.getAction()) {
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
                        if(destScrollX > deleteViewWidth){
                            destScrollX = deleteViewWidth;
                        }
                    }
                    scrollTo(destScrollX, 0);

                    beginX = curX;
                    break;
                case MotionEvent.ACTION_UP:
                    endX = event.getRawX();
                    if(scrollX <= deleteViewWidth/2){
                        scrollToHideDeleteView(scrollX);
                    }else{
                        scrollToHideDeleteView(scrollX);
                    }
                    break;
                default:
                    break;
            }

        }
        return true;
    }*/




}
