package com.example.zhaojw.testcustomview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.example.zhaojw.testcustomview.R;

/**
 * Created by zhaojianwu on 2018/3/13.
 */

public class VerticalAdActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_vertical_ad);
        initView();

    }

    private void initView(){
    }
}
