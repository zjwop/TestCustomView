package com.example.zhaojw.testcustomview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.zhaojw.testcustomview.R;
import com.example.zhaojw.testcustomview.view.PullToRefreshLayout;

public class PullToRefreshLayoutActivity extends Activity {


    private PullToRefreshLayout pullToRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_pull_to_refresh_layout);
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pullToRefreshLayout);

        initView();

    }

    private void initView(){
    }
}
