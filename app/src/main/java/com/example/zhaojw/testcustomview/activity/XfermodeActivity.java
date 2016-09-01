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
public class XfermodeActivity extends Activity {


    private PorterDuffXfermodeView porterDuffXfermodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_xfermode);
        initView();

    }

    private void initView(){
        porterDuffXfermodeView = (PorterDuffXfermodeView) findViewById(R.id.porterDuffXfermodeView);
    }
}
