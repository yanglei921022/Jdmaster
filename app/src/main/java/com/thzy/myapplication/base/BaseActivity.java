package com.thzy.myapplication.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.thzy.myapplication.application.MyApplication;
import com.thzy.myapplication.utils.DataStorageUtils;
import com.thzy.myapplication.utils.LogUtils;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/30.
 */

public  class BaseActivity extends AppCompatActivity{
    private MyApplication appliaction;
    private DataStorageUtils dataStorageUtils;
    private String tag = this.getClass().getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        appliaction = MyApplication.getMyAppliaction();
        dataStorageUtils = new DataStorageUtils(this);

        //PushAgent.getInstance(this).onAppStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


        }
    }

    /**
     * 添加activity 控制activity的关闭
     */
    public void addActivity(){
        appliaction.addActivity(this);
    }

    /**
     * 移除当前的页面管理器的页面
     */
    public void removeActivity(){
        appliaction.removeActivity(this);
    }
    /**
     * 销毁当前的activity
     */
    public void destroyActivity(){
        appliaction.destroyActivity(this);
    }
    /**
     * 界面销毁 (1)关闭所有activity
     */
    public void finishAll() {
        appliaction.finishAll();
    }
    /**
     * 界面销毁 (2)退出整个程序
     */
    public void exit() {
        appliaction.exit();
    }
    /**
     * 不设置flags,不需要返回值的页面跳转
     *
     *
     *            当前Activity
     * @param c
     *            要跳转的class
     * @param map
     *            传输数据的键值对 没有时为null
     * @param b
     *            是否关闭当前页面 为true时关闭,false时保留
     */
    public void activityPageChange(Class<?> c, Map<String, Object> map,
                                   boolean b) {
        // 这里的页面关闭可控制
        appliaction.activityPageChange(this, c, map, -1, false, 0, b);
    }

    /**
     * 设置flags,不需要返回值的页面跳转
     *
     *
     *            当前Activity
     * @param c
     *            要跳转的class
     * @param map
     *            传输数据的键值对 没有时为null
     * @param flags
     *            为要跳转的页面设置的flags 没有时为-1
     * @param b
     *            是否关闭当前页面 为true时关闭,false时保留
     */
    public void activityPageChange(Class<?> c, Map<String, Object> map,
                                   int flags, boolean b) {
        // 这里的页面关闭可控制
        appliaction.activityPageChange(this, c, map, flags, false, 0, b);
    }

    /**
     * 设置flags,需要返回值的页面跳转
     *
     *
     *            当前Activity
     * @param c
     *            要跳转的class
     * @param map
     *            传输数据的键值对 没有时为null
     * @param flags
     *            为要跳转的页面设置的flags 没有时为-1
     * @param requestCode
     *            跳转页面调用startActivityForResult() 对应的请求码
     * @param b
     *            是否关闭当前页面 为true时关闭,false时保留
     */
    public void activityPageChange(Class<?> c, Map<String, Object> map,
                                   int flags, int requestCode, boolean b) {
        // 这里的页面关闭可控制
        appliaction.activityPageChange(this, c, map, flags, true,
                requestCode, b);
    }

    /**
     * 获取页面传输过程传输的非基本类型数据
     *
     * @param key
     * @return
     */
    public Object getMapData(String key) {
        return appliaction.getMapData(key);
    }

    /**
     * 判断内存卡是否存在
     * @return
     */
    public static boolean isSDCardExist(){
        return DataStorageUtils.isSDCardExist();
    }

    /**
     * 获取sd卡剩余的空间大小 单位是byte
     * @return
     */
    public static long getSDFreeSize(){
        return DataStorageUtils.getSDFreeSize();
    }
    /**
     * 根据键字符串，存储一个字符串值
     *
     * @param key
     * @param value
     * @return 返回提交是否成功
     */
    public boolean putString(String key, String value) {
        return dataStorageUtils.putString(key, value);
    }

    /**
     * 根据key值得到存储结果，如果没有找到value就返回null
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        return dataStorageUtils.getString(key);
    }
    public boolean putBoolean(String key, boolean value) {
        return dataStorageUtils.putBoolean(key, value);
    }
    public boolean getBoolean(String key){
        return dataStorageUtils.getBoolean(key);
    }

    /**
     * 根据键字符串，存储一个整型值, 值不能为-1
     *
     * @param key
     * @param value
     *            不能为-1
     * @return 返回提交是否成功
     */
    public boolean putInt(String key, int value) {
        return dataStorageUtils.putInt(key, value);
    }

    /**
     * 根据key值得到存储结果，如果没有找到value就返回-1
     *
     * @param key
     * @return
     */
    public int getInt(String key) {
        return dataStorageUtils.getInt(key);
    }

    /**
     * 根据key值移除对应的数据
     *
     * @param key
     * @return
     */
    public boolean remove(String key) {
        return dataStorageUtils.remove(key);
    }

    /**
     * 吐司
     * @param message
     */
    public void toast(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
    public void log(String s){
        LogUtils.i(tag,s);
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        appliaction = null;
        dataStorageUtils = null;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();

    }
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
