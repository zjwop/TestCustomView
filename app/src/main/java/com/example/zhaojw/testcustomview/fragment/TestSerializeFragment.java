package com.example.zhaojw.testcustomview.fragment;

/**
 * Created by zhaojianwu on 2017/8/2.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhaojw.testcustomview.R;
import com.example.zhaojw.testcustomview.activity.TestActivity2;
import com.example.zhaojw.testcustomview.model.TestSerializeModel;

/**
 * Created by zjw on 2016/9/15.
 */
public class TestSerializeFragment extends Fragment {

    private TextView textView;
    private Button button;

    private TestSerializeModel serializeModel;

    public static TestSerializeFragment newInstance(Bundle args) {
        TestSerializeFragment fragment = new TestSerializeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("zhaojw/log", "onCreate");
        Bundle args = getArguments();
        serializeModel = (TestSerializeModel)args.getSerializable("serialize_model");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("zhaojw/log", "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_test, null);
        textView = (TextView) rootView.findViewById(R.id.tv_fragment);
        //textView.setText("" + serializeModel.id);
        textView.setTextSize(30);

        String content = "赵健午赵健午赵健午赵健午赵健午哈哈";
        String red = "哈哈";

        String sum = content + red;

        SpannableString totalPrice = new SpannableString(sum);
        totalPrice.setSpan(new TextAppearanceSpan(getActivity(), R.style.text_17_e29b03), content.length(), sum.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(totalPrice);

        button = (Button)rootView.findViewById(R.id.btn_fragment);
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                serializeModel.id++;
//                textView.setText("" + serializeModel.id);
//                ((TestActivity)getActivity()).onBtnClick();
                Intent intent = new Intent(getActivity(), TestActivity2.class);
                intent.putExtra("testKey", "zhaojw");
                startActivity(intent);
            }
        });
        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i("zhaojw/log", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("zhaojw/log", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("zhaojw/log", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("zhaojw/log", "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("zhaojw/log", "onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("zhaojw/log", "onDestroyView");
    }
}
