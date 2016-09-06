package com.example.zhaojw.testcustomview.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by zjw on 2016/9/6.
 */
public class ImageShowerView extends RelativeLayout{

    private Context mContext;
    private ViewPager mViewPager;
    private LinearLayout indicatorContainer;

    public ImageShowerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        initViewPager();
        initIndicator();
    }

    private void initView(){
        mViewPager = new ViewPager(mContext);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mViewPager.setLayoutParams(layoutParams);
        mViewPager.setBackgroundColor(Color.GREEN);
        addView(mViewPager);

        indicatorContainer = new LinearLayout(mContext);
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.width = 500;
        layoutParams.height = 300;
        indicatorContainer.setLayoutParams(layoutParams);
        indicatorContainer.setBackgroundColor(Color.GRAY);
        addView(indicatorContainer);

    }

    private void initViewPager(){

    }

    private void initIndicator(){

    }
}
