package com.example.zhaojw.testcustomview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.zhaojw.testcustomview.R;
import com.example.zhaojw.testcustomview.view.FlowLayout;
import com.example.zhaojw.testcustomview.view.HorizontalFlowView;

public class FlowLayoutActivity extends Activity {

    private HorizontalFlowView horizontalFlowView;
    private FlowLayout flowLayout;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_flow_layout);
        initView();

    }

    private void initView(){
        horizontalFlowView = (HorizontalFlowView) findViewById(R.id.horizontalFlowView);
        flowLayout = (FlowLayout) findViewById(R.id.flowLayout);
        editText = (EditText)findViewById(R.id.editText);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = editText.getText().toString();
                horizontalFlowView.addChild(string);
                flowLayout.addChild(string);

            }
        });
    }
}
