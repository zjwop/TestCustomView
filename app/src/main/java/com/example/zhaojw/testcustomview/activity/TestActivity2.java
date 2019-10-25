package com.example.zhaojw.testcustomview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhaojw.testcustomview.R;

import java.util.ArrayList;

/**
 * Created by zhaojianwu on 2018/5/30.
 */

public class TestActivity2 extends AppCompatActivity {

    private Button button;
    private Bundle savedInstanceState;

    private HorizontalScrollView scrollView;
    private ImageView scrollCover;
    private LinearLayout container;
    private Button addViewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("zhaojianwu", "TestActivity2 onCreate()");
        setContentView(R.layout.activity_test_2);

        if (getIntent() != null) {
            String str = getIntent().getStringExtra("testKey");
            System.out.println(str);
        }

        button = (Button)findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> list = new ArrayList<>();
                list.get(1);
            }
        });

        scrollView = findViewById(R.id.scroll);
        scrollCover = findViewById(R.id.scrollcover);
        container = findViewById(R.id.llcontainer);
        addViewBtn = findViewById(R.id.addBtn);

        addViewBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final int childCount = container.getChildCount();
                if (childCount >= 1) {
                    final TextView textView = new TextView(TestActivity2.this);
                    textView.setText("text" + (childCount-1));
                    container.addView(textView, childCount-1, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

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
        });

        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                Log.i("zhaojianwu", "scrollView width " + scrollView.getWidth());
                Log.i("zhaojianwu", "container width " + container.getWidth());
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        Log.i("zhaojianwu", "TestActivity2 onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("zhaojianwu", "TestActivity2 onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("zhaojianwu", "TestActivity2 onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("zhaojianwu", "TestActivity2 onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("zhaojianwu", "TestActivity2 onDestroy()");
    }


}
