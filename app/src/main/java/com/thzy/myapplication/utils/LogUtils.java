package com.thzy.myapplication.utils;

import android.util.Log;

/**
 * 封装打印工具类 添加控制开启日志的配置:OPENLOG
 * 当开发的时候设为true,线上版本设为false
 * @author Alba
 *
 */
public class LogUtils {

    private final static boolean OPENLOG = true;

    public static void i(String tag, String content) {
        if (OPENLOG) {
            Log.i(tag, content);
        }

    }

    public static void v(String tag, String content) {
        if (OPENLOG) {
            Log.v(tag, content);
        }

    }

    public static void d(String tag, String content) {
        if (OPENLOG) {
            Log.d(tag, content);
        }

    }

    public static void w(String tag, String content) {
        if (OPENLOG) {
            Log.w(tag, content);
        }

    }

    public static void e(String tag, String content) {
        if (OPENLOG) {
            Log.e(tag, content);
        }

    }
}
