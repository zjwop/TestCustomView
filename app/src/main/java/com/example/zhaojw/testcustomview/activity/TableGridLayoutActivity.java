package com.example.zhaojw.testcustomview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.zhaojw.testcustomview.R;
import com.example.zhaojw.testcustomview.view.TableGridLayout;

public class TableGridLayoutActivity extends AppCompatActivity {


    private TableGridLayout tableGridLayout;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_grid_layout);
        initView();

    }

    private void initView(){
        tableGridLayout = (TableGridLayout) findViewById(R.id.tableGridLayout);
        editText = (EditText)findViewById(R.id.editText);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = editText.getText().toString();
                tableGridLayout.addChild(string);
            }
        });
    }
}