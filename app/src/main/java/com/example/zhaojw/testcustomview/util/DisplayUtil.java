package com.example.zhaojw.testcustomview.util;

import android.content.Context;

/**
 * Created by zjw on 2016/9/15.
 */
public class DisplayUtil {

    public static int dp2px(Context context, float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        int pxValue = (int)(dpValue * scale + 0.5f);
        return pxValue;
    }

    public static int px2dp(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        int dpValue = (int)(pxValue/scale + 0.5f);
        return dpValue;
    }
}
