package com.example.zhaojw.testcustomview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhaojw.testcustomview.R;

import java.util.ArrayList;

/**
 * Created by zhaojianwu on 2018/10/25.
 */

public class TestListViewActivity extends Activity {

    private ListView listView;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_test_list_view);
        initView();

    }

    private void initView(){

        listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        final ArrayList<String> datas = new ArrayList<String>();
        for(int i = 0; i < 20; i++){
            datas.add(i+"");
        }
        ArrayAdapter<String> adatper = new ArrayAdapter<String>(this,R.layout.main_list_item,R.id.main_list_text,datas);
        listView.setAdapter(adatper);

        TextView header1 = new TextView(this);
        header1.setText("header1");

        TextView header2 = new TextView(this);
        header2.setText("header2");

        listView.addHeaderView(header1);
        listView.addHeaderView(header2);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = 0;
                try {
                    position = Integer.valueOf(editText.getText().toString());
                } catch (Exception e) {
                    position = 0;
                }

                //验证说明 headerView 和 listItem 会统一计算positon

                if (position >=0 && position < datas.size() + 2) {
                    final int id = position;
                    listView.smoothScrollToPositionFromTop(position, 0, 800);

                }

            }
        });

    }
}
