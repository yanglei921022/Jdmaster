package com.thzy.myapplication.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;


import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * Created by Administrator on 2017/11/30.
 */

public class MyApplication extends Application {
    //存放activity的集合
    private List<Activity> activities;
    //页面切换传递数据的集合
    private Map<String, Object> transmitDataMap;
    /**
     * 利用单例模式获取application
     */

    private static MyApplication appliaction = null;

    public static synchronized MyApplication getMyAppliaction() {
        return appliaction;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appliaction = this;
        activities = new ArrayList<Activity>();
        transmitDataMap = new HashMap<String, Object>();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

   /* public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }*/

    /**
     * 添加activity
     */
    public void addActivity(Activity activity) {
        if (!hasActivity(activity)) {
            activities.add(activity);
        }
    }

    /**
     * 判断对应activity是否存在
     */
    private boolean hasActivity(Activity activity) {

        for (int i = 0; i < activities.size(); i++) {
            Activity a = activities.get(i);
            if (a == activity) {
                return true;
            }
        }
        return false;
    }

    /**
     * 移除对应activity
     */
    public void removeActivity(Activity activity) {
        if (hasActivity(activity)) {
            activities.remove(activity);
        }
    }

    /**
     * 清除所有activity
     */
    public void clearActivity() {
        activities.clear();
    }

    /**
     * 销毁当前activity
     */
    public void destroyActivity(Activity a) {
        removeActivity(a);
        a.finish();
    }

    /**
     * 界面销毁 (1)关闭所有activity
     */
    public void finishAll() {
        if (activities.size() > 0) {
            for (Activity activity : activities) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
            clearActivity();
        }
    }

    /**
     * 界面销毁 (2)退出整个程序
     */
    public void exit() {
        // wipeMapData();
        // finishAll();
        // 杀死了整个进程
        System.exit(0);
    }

    /**
     * activityPageChange 页面(Activity)切换 TransmitData 传递数据 (包含基本类型数据和非基本类型数据，
     * 非基本类型数据通过传递过去的key值到transmitDataMap中去取 )
     *
     * @param a           当前Activity
     * @param c           要跳转的class
     * @param map         传输数据的键值对 没有时为null
     * @param flags       为要跳转的页面设置的flags 没有时为-1
     * @param isForResult 跳转对应页面时是调用startActivity()还是调用startActivityForResult()
     *                    默认时为false,调用startActivity(),此时下一个参数requestCode无效
     * @param requestCode 跳转页面调用startActivityForResult() 对应的请求码
     * @param b           是否关闭当前页面 为true时关闭,false时保留
     */
    public void activityPageChange(Activity a, Class<?> c,
                                   Map<String, Object> map, int flags, boolean isForResult,
                                   int requestCode, boolean b) {
        if (a == null || c == null) {
            return;
        }

        Intent intent = new Intent(a, c);
        Bundle bundle = null;

        if (map != null && map.size() > 0) {
            bundle = new Bundle();
            Set<String> keySet = map.keySet();
            for (String i : keySet) {
                Object o = map.get(i);

                if (o instanceof Integer) {
                    bundle.putInt(i, (Integer) o);
                } else if (o instanceof Double) {
                    bundle.putDouble(i, (Double) o);
                } else if (o instanceof Boolean) {
                    bundle.putBoolean(i, (Boolean) o);
                } else if (o instanceof String) {
                    bundle.putString(i, (String) o);
                } else {// 非基本数据类型
                    saveMapData(i, o);
                    bundle.putString(i, i);
                }

            }

        }

        if (flags != -1) {
            intent.setFlags(flags);
        }

        if (bundle != null) {
            intent.putExtras(bundle);
        }

        if (!isForResult) {
            a.startActivity(intent);
        } else {
            a.startActivityForResult(intent, requestCode);
        }

        if (b) {
            destroyActivity(a);
        }
    }

    /**
     * transmitDataMap存储非基本类型数据
     *
     * @param key
     * @param value
     */
    private void saveMapData(String key, Object value) {
        transmitDataMap.put(key, value);
    }

    /**
     * transmitDataMap获取非基本类型数据
     *
     * @param key
     * @return
     */
    public Object getMapData(String key) {
        return transmitDataMap.get(key);
    }

    /**
     * transmitDataMap清空数据 应当在退出app时调用
     */
    private void wipeMapData() {
        if (transmitDataMap.size() > 0) {
            transmitDataMap.clear();
        }
    }

}
