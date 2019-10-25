package com.example.zhaojw.testcustomview.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhaojw.testcustomview.R;

/**
 * Created by zhaojianwu on 2019-10-25.
 */
public class HorizontalFlowView extends FrameLayout {


    private HorizontalScrollView scrollView;
    private ImageView scrollCover;
    private LinearLayout container;

    public HorizontalFlowView(Context context) {
        this(context, null);
    }

    public HorizontalFlowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalFlowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rootView = inflater.inflate(R.layout.horizontal_flow_view, this);

        scrollView = rootView.findViewById(R.id.scroll);
        scrollCover = rootView.findViewById(R.id.scrollcover);
        container = rootView.findViewById(R.id.llcontainer);
    }

    public void addChild(String text) {
        final int childCount = container.getChildCount();
        if (childCount >= 1) {
            final TextView textView = new TextView(getContext());
            textView.setText(text);
            textView.setTextSize(20);
            textView.setBackgroundColor(Color.parseColor("#123456"));
            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.leftMargin = 20;
            container.addView(textView, childCount-1, ll);

            container.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    final int scrollx = container.getWidth() - scrollView.getWidth();
                    if (scrollx > 0) {
                        scrollView.setDrawingCacheEnabled(true);
                        scrollCover.setImageBitmap(scrollView.getDrawingCache());
                        scrollCover.setVisibility(View.VISIBLE);

                        scrollView.scrollTo(scrollx, 0);
                        scrollCover.setVisibility(View.GONE);

                    }
                    container.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });

        }
    }
}
