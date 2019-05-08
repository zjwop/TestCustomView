package com.example.zhaojw.testcustomview.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhaojw.testcustomview.R;
import com.example.zhaojw.testcustomview.activity.TestActivity2;


/**
 * Created by zjw on 2016/9/15.
 */
public class TestFragment extends Fragment{

    private TextView textView;
    private String text;

    private Button btn;

    public static TestFragment newInstance(Bundle args) {
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        text = args.getString("fragment_text");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test, null);
        textView = (TextView) rootView.findViewById(R.id.tv_fragment);
        textView.setText(text);

        btn = (Button) rootView.findViewById(R.id.btn_fragment);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestActivity2.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
