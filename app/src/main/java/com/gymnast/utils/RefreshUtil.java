package com.gymnast.utils;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;

/**
 * Created by zzqybyb19860112 on 2016/9/17.
 */
public class RefreshUtil {
    public static void refresh(SwipeRefreshLayout swipeLayout,Activity activity){
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        //进界面就刷新
        swipeLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, activity.getResources()
                        .getDisplayMetrics()));
        swipeLayout.setRefreshing(true);
    }
}
