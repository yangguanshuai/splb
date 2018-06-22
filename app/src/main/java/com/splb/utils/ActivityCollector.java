package com.splb.utils;

import com.mylibrary.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {

    public static List<BaseActivity> activities = new ArrayList<BaseActivity>();

    public static void addActivity(BaseActivity activity) {
        activities.add(activity);
    }

    public static void removeActivity(BaseActivity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (BaseActivity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
