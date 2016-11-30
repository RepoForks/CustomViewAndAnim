package com.util.customview.util;

import android.content.Context;

/**
 * Created by hai.ning on 16/2/2.
 */
public class CommonUtil {

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

}
