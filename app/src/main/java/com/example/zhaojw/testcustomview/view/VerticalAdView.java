package com.example.zhaojw.testcustomview.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by zhaojianwu on 2018/3/13.
 */

public class VerticalAdView extends FrameLayout {

    private TextView mRootView;


    public VerticalAdView(@NonNull Context context) {
        super(context);
        initView();
    }

    public VerticalAdView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public VerticalAdView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        mRootView = new TextView(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRootView.setBackgroundColor(Color.GREEN);
        mRootView.setText("zhaojwtest");
        mRootView.setGravity(Gravity.CENTER);
        addView(mRootView, layoutParams);
    }
}
