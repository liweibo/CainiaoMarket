package com.huake.lymarket.utils;

/*
 * @创建者     兰昱
 * @创建时间  2016/10/9 20:14
 * @描述	      
 */

import android.content.Context;

public class CommonUtils {

    public static int dip2px(int dp, Context context){
        float density =context.getResources().getDisplayMetrics().density;
        //四舍五入
        int px= (int) (density*dp+.5f);
        return px;
    }

    //px转dp
    public static int px2dp(int px,Context context){
        float density = context.getResources().getDisplayMetrics().density;
        int dp= (int) (px/density+.5f);
        return dp;
    }
}
