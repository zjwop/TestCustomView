package com.example.zhaojw.testcustomview.activity;

/**
 * Created by zhaojianwu on 2017/2/21.
 */


import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import com.example.zhaojw.testcustomview.R;
import com.example.zhaojw.testcustomview.fragment.TestSerializeFragment;
import com.example.zhaojw.testcustomview.model.TestSerializeModel;

public class TestActivity extends AppCompatActivity {

    private TestSerializeModel serializeModel = new TestSerializeModel();
    private TextView textView;

    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Parcelable p = savedInstanceState.getParcelable("android:support:fragments");
            Log.i("zhaojianwu", p != null ? "have data": "null");
        }
        Log.i("zhaojianwu", "TestActivity onCreate()");
        super.onCreate(savedInstanceState);

        this.savedInstanceState = savedInstanceState;
        Log.i("zhaojianwu", "TestActivity onCreate()");
        setContentView(R.layout.activity_test);

        serializeModel.id = 1;

        textView = (TextView)findViewById(R.id.tv_test_activity);
        textView.setText(""+serializeModel.id);

        //TODO
        String mac = "";
        WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        try {
            mac = wifiManager.getConnectionInfo().getMacAddress();
        } catch (SecurityException e) {

        }

        String imei = "";
        TelephonyManager telephonyManager = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        try {
            imei = telephonyManager.getDeviceId();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        textView.setText("mac:" + mac + " " + "imei:" + imei);


        FragmentManager fragmentManager = getSupportFragmentManager();
        TestSerializeFragment fragment = (TestSerializeFragment)fragmentManager.findFragmentByTag("TestSerializeFragment");
        if (fragment == null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("serialize_model", serializeModel);
            fragment = TestSerializeFragment.newInstance(bundle);

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.container, fragment, "TestSerializeFragment");
            transaction.commit();
        }

    }

    public void onBtnClick() {
        textView.setText(""+serializeModel.id);
    }

    private void initView(){



    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i("zhaojianwu", "TestActivity onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("zhaojianwu", "TestActivity onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("zhaojianwu", "TestActivity onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("zhaojianwu", "TestActivity onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("zhaojianwu", "TestActivity onDestroy()");
    }
}

