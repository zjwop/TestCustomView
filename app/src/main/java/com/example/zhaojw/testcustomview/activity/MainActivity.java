package com.example.zhaojw.testcustomview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.zhaojw.testcustomview.R;

import java.util.ArrayList;

/**
 * Created by zjw on 2016/8/28.
 */
public class MainActivity extends AppCompatActivity {


    private ListView listView;
    private static final String[] strings = {
                                "TestActivity",
                                "SlidingMenu And SlidingDelete",
                                "PullToRefreshLayout",
                                "TableGridLayout",
                                "XfermodeView And CircleView",
                                "ImageShower",
                                "ScrollTitleBar",
                                "VerticalAdView",
                                "TestListView"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("zhaojianwu", "MainActivity onCreate()");
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.main_listView);
        initView();

    }

    private void initView(){

        ArrayList<String> datas = new ArrayList<String>();
        for(int i = 0; i < strings.length; i++){
            datas.add(strings[i]);
        }
        ArrayAdapter<String> adatper = new ArrayAdapter<String>(this,R.layout.main_list_item,R.id.main_list_text,datas);
        listView.setAdapter(adatper);
        listView.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
               switch(position){
                   //TODO add activity here
                   case 0:
                       intent = new Intent(MainActivity.this, TestActivity.class);
                       startActivity(intent);
                       break;
                   case 1:
                       intent = new Intent(MainActivity.this, SlidingMenuAndDeleteLayoutActivity.class);
                       startActivity(intent);
                       break;

                   case 2:
                       intent = new Intent(MainActivity.this, PullToRefreshLayoutActivity.class);
                       startActivity(intent);
                       break;

                   case 3:
                       intent = new Intent(MainActivity.this, TableGridLayoutActivity.class);
                       startActivity(intent);
                       break;

                   case 4:
                       intent = new Intent(MainActivity.this, XfermodeActivity.class);
                       startActivity(intent);
                       break;

                   case 5:
                       intent = new Intent(MainActivity.this, ImageShowerActivity.class);
                       startActivity(intent);
                       break;

                   case 6:
                       intent = new Intent(MainActivity.this, ScrollTitleBarActivity.class);
                       startActivity(intent);
                       break;

                   case 7:
                       // TODO
                       intent = new Intent(MainActivity.this, VerticalAdActivity.class);
                       startActivity(intent);
                       break;
                   case 8:
                       intent = new Intent(MainActivity.this, TestListViewActivity.class);
                       startActivity(intent);
                       break;

               }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i("zhaojianwu", "MainActivity onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("zhaojianwu", "MainActivity onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("zhaojianwu", "MainActivity onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("zhaojianwu", "MainActivity onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("zhaojianwu", "MainActivity onDestroy()");
    }
}
