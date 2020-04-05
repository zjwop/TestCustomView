package com.example.zhaojw.testcustomview.activity;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhaojw.testcustomview.R;
import com.example.zhaojw.testcustomview.util.PingUtil;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by zhaojianwu on 2020/2/2.
 */

public class TestNetworkActivity extends Activity {

    private Button pingSuccess;
    private Button pingFail;
    private TextView wifiText;
    private WifiManager wifiManager;
    private WifiInfo wifiInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_test_network);
        initView();
        wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();
        String info = "getSSID()="+wifiInfo.getSSID()+"\n"
                +"getBSSID()="+wifiInfo.getBSSID()+"\n"
                +"getHiddenSSID()="+wifiInfo.getHiddenSSID()+"\n"
                +"getLinkSpeed()="+wifiInfo.getLinkSpeed()+"\n"
                +"getMacAddress()="+wifiInfo.getMacAddress()+"\n"
                +"getNetworkId()="+wifiInfo.getNetworkId()+"\n"
                +"getRssi()="+wifiInfo.getRssi()+"\n"
                +"getSupplicantState()="+wifiInfo.getSupplicantState()+"\n"
                +"getDetailedStateOf()="+wifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState());
        wifiText.setText(info);
        Log.e("wifi", String.valueOf(wifiManager.getWifiState()));
        Log.e("wifi",int2ip(wifiInfo.getIpAddress()));
        Log.e("wifi",getIpAddress());
    }

    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 255).append(".");
        sb.append(ipInt >> 8 & 255).append(".");
        sb.append(ipInt >> 16 & 255).append(".");
        sb.append(ipInt >> 24 & 255);
        return sb.toString();
    }

    public static String getLocalIpAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager)context.getSystemService("wifi");
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return int2ip(i);
        } catch (Exception var4) {
            return null;
        }
    }

    //获取IP地址
    public static String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initView(){
        pingSuccess = (Button) findViewById(R.id.button_reachable);
        pingFail = (Button) findViewById(R.id.button_non_reachable);
        wifiText = findViewById(R.id.wifi_info);
        pingSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PingUtil.ping("192.168.1.1", 5, null);
            }
        });

        pingFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PingUtil.ping("192.168.1.111", 5, null);
            }
        });

    }
}
