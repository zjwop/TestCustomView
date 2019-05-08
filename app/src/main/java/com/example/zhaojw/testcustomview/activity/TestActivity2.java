package com.example.zhaojw.testcustomview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.zhaojw.testcustomview.R;

import java.util.ArrayList;

/**
 * Created by zhaojianwu on 2018/5/30.
 */

public class TestActivity2 extends AppCompatActivity {

    private Button button;
    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("zhaojianwu", "TestActivity2 onCreate()");
        setContentView(R.layout.activity_test_2);

        button = (Button)findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> list = new ArrayList<>();
                list.get(1);
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
