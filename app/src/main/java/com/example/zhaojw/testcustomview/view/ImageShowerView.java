package com.example.zhaojw.testcustomview.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhaojw.testcustomview.R;

import java.util.ArrayList;

/**
 * Created by zjw on 2016/9/6.
 */
public class ImageShowerView extends RelativeLayout {

    private Context mContext;
    private ViewPager mViewPager;
    private LinearLayout indicatorContainer;

    private PagerAdapter adapter;
    private ArrayList<View> viewContainer = new ArrayList<View>();
    private int[] imageIds = {R.drawable.test1, R.drawable.test2, R.drawable.test3, R.drawable.test4, R.drawable.test5};

    public ImageShowerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        initIndicator();
        initViewPager();
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

        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(mContext);
            ViewGroup.LayoutParams tvlayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(tvlayoutParams);
            imageView.setImageDrawable(getResources().getDrawable(imageIds[i]));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewContainer.add(imageView);
        }

    }

    private void initViewPager() {

        adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager) container).addView(viewContainer.get(position % viewContainer.size()));
                Log.d("viewpager", " Create Position: " + String.valueOf(position % viewContainer.size()));
                return viewContainer.get(position % viewContainer.size());
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager) container).removeView(viewContainer.get(position % viewContainer.size()));
                Log.d("viewpager", "Destroy Position: " + String.valueOf(position % viewContainer.size()));
            }
        };

        mViewPager.setAdapter(adapter);

        //设置显示首张图片的View的id
        int initViewId = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % viewContainer.size());
        //int initViewId = Integer.MAX_VALUE / 2;
        mViewPager.setCurrentItem(initViewId);
        int initIndex = initViewId % viewContainer.size();
        indicatorContainer.getChildAt(initIndex).setBackgroundColor(Color.BLACK);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int index = position % viewContainer.size();
                indicatorContainer.getChildAt(index).setBackgroundColor(Color.BLACK);
                for (int i = 0; i < imageIds.length; i++) {
                    if (i != index) {
                        indicatorContainer.getChildAt(i).setBackgroundColor(Color.WHITE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
}
