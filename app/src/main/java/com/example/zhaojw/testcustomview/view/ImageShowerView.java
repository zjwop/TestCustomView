package com.example.zhaojw.testcustomview.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.zhaojw.testcustomview.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by zjw on 2016/9/6.
 */
public class ImageShowerView extends RelativeLayout {

    private Context mContext;
    private ViewPager mViewPager;
    private LinearLayout indicatorContainer;

    //表示当前正在显示的Page的Id
    private int curPageId;
    //表示ViewPager中共装载的Pages数
    private int pageCount;

    private PagerAdapter adapter;
    private ArrayList<View> viewContainer = new ArrayList<View>();
    private int[] imageIds = {R.drawable.test1, R.drawable.test2, R.drawable.test3, R.drawable.test4, R.drawable.test5};

    //自动播放下一张图片的时间
    private static int AUTO_PLAY_MESSAGE = 1;
    private static long AUTO_PLAY_NEXT_PAGE_TIME = 3000;
    private boolean isAutoPlay = true;
    private Handler mHandler;

    public ImageShowerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        //通过重复一组来模拟无限循环
        pageCount = imageIds.length * 2;
        initView();
        initIndicator();
        initViewPager();

        setAutoPlay();
    }


    private void initView() {
        mViewPager = new ViewPager(mContext);
        mViewPager.setBackgroundColor(Color.GREEN);
        addView(mViewPager);

        indicatorContainer = new LinearLayout(mContext);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.bottomMargin = 20;
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        indicatorContainer.setLayoutParams(lp);
        indicatorContainer.setBackgroundColor(Color.TRANSPARENT);
        addView(indicatorContainer);

        //数据ImageView复制一份，如果共用会报子View需要先从parent中移除的错误，复制一份虽然内存占用增加，但不报错
        for(int i = 0; i < 2; i++){
            for (int j = 0; j < imageIds.length; j++) {
                ImageView imageView = new ImageView(mContext);
                ViewGroup.LayoutParams tvlayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(tvlayoutParams);
                imageView.setImageDrawable(getResources().getDrawable(imageIds[j]));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                viewContainer.add(imageView);
            }
        }


    }

    private void initViewPager() {

        adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return pageCount;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = viewContainer.get(position);
                if (view.getParent() != null) {
                    ((ViewGroup) view.getParent()).removeView(view);
                }
                container.addView(view);
                Log.d("viewpager", " Create Position: " + String.valueOf(position % imageIds.length));
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                View view = viewContainer.get(position);
                container.removeView(view);
                Log.d("viewpager", "Destroy Position: " + String.valueOf(position % imageIds.length));
            }
        };

        mViewPager.setAdapter(adapter);
        //设置切换时的过渡效果；
        //setViewPagerScrollSpeed();
        //设置显示首张图片的View的id
        int initPageId = imageIds.length;
        curPageId = initPageId;
        mViewPager.setCurrentItem(initPageId, false);
        int initIndex = initPageId % imageIds.length;
        indicatorContainer.getChildAt(initIndex).setBackgroundColor(Color.BLACK);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curPageId = position;
                int index = position % imageIds.length;
                indicatorContainer.getChildAt(index).setBackgroundColor(Color.BLACK);
                for (int i = 0; i < imageIds.length; i++) {
                    if (i != index) {
                        indicatorContainer.getChildAt(i).setBackgroundColor(Color.WHITE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // 完全停止以后才设置
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (curPageId == 0) {
                        mViewPager.setCurrentItem(curPageId + imageIds.length, false);
                    } else if (curPageId == pageCount - 1) {
                        mViewPager.setCurrentItem(curPageId - imageIds.length, false);
                    }
                }

            }
        });
    }

    private void initIndicator() {
        for (int i = 0; i < imageIds.length; i++) {
            TextView indicator = new TextView(mContext);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50);
            layoutParams.setMargins(10, 10, 10, 10);
            indicator.setLayoutParams(layoutParams);
            indicator.setBackgroundColor(Color.WHITE);
            indicatorContainer.addView(indicator);
        }

    }

    private void setAutoPlay(){
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == AUTO_PLAY_MESSAGE){
                    mViewPager.setCurrentItem(curPageId + 1);
                    mHandler.sendEmptyMessageDelayed(AUTO_PLAY_MESSAGE, AUTO_PLAY_NEXT_PAGE_TIME);
                }

            }
        };
        if(isAutoPlay){
            mHandler.sendEmptyMessageDelayed(AUTO_PLAY_MESSAGE, AUTO_PLAY_NEXT_PAGE_TIME);
        }
    }

    private void setViewPagerScrollSpeed( ){
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller( mViewPager.getContext( ) );
            mScroller.set( mViewPager, scroller);
        }catch(NoSuchFieldException e){

        }catch (IllegalArgumentException e){

        }catch (IllegalAccessException e){

        }
    }


    public class FixedSpeedScroller extends Scroller {
        private int mDuration = 0;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }


        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }
    }
}
