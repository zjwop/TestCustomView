package com.example.zhaojw.testcustomview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.zhaojw.testcustomview.R;
import com.example.zhaojw.testcustomview.view.PullToRefreshLayout;

public class PullToRefreshLayoutActivity extends AppCompatActivity {


    private PullToRefreshLayout pullToRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh_layout);
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pullToRefreshLayout);

        initView();

    }

    private void initView(){
    }
}
