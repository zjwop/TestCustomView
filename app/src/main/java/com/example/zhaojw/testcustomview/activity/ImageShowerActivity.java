package com.example.zhaojw.testcustomview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.zhaojw.testcustomview.R;
import com.example.zhaojw.testcustomview.view.PorterDuffXfermodeView;

/**
 * Created by zjw on 2016/8/28.
 */
public class ImageShowerActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image_shower);
        initView();

    }

    private void initView(){

    }
}
