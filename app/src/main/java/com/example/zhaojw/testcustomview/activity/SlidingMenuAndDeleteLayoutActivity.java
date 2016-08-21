package com.example.zhaojw.testcustomview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.zhaojw.testcustomview.R;
import com.example.zhaojw.testcustomview.view.SlidingDeleteLayout;
import com.example.zhaojw.testcustomview.view.SlidingMenuLayout;

import java.util.ArrayList;

public class SlidingMenuAndDeleteLayoutActivity extends AppCompatActivity {


    private SlidingDeleteLayout slidingDeleteLayout;
    private SlidingMenuLayout slidingMenuLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_menu_and_delete_layout);
        slidingDeleteLayout = (SlidingDeleteLayout) findViewById(R.id.slidingDeleteLayout);
        slidingDeleteLayout.setIconText("1");


        slidingMenuLayout = (SlidingMenuLayout) findViewById(R.id.slidingMenuLayout);
        initSlidingMenuLayout();

    }

    private void initSlidingMenuLayout(){
        ListView listView = new ListView(this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        listView.setLayoutParams(layoutParams);
        ArrayList<String> datas = new ArrayList<String>();
        for(int i = 0; i < 30; i++){
            datas.add("test item id "+ i);
        }
        ArrayAdapter<String> adatper = new ArrayAdapter<String>(this,R.layout.list_item,R.id.list_text,datas);
        listView.setAdapter(adatper);

        FrameLayout container = (FrameLayout)slidingMenuLayout.getContainer();
        container.addView(listView);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}
