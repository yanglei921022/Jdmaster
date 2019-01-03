package com.thzy.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;


import com.thzy.myapplication.application.MyApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/30.
 */

public class DataStorageUtils {
    private final static String TAG = "hyh";
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    /** 当存在SD时，且空间足够时，app文件下载存放的目录 */
    public final static String SDCARD_STORAGE = Environment
            .getExternalStorageDirectory() + File.separator + "EARSUN";
    /** 当不存在SD时，app文件下载存放的目录 */
    public final static String INNER_STORAGE = MyApplication
            .getMyAppliaction().getFilesDir() + File.separator;

    public DataStorageUtils(Context context) {
        // 貌似都是同一文件名存储，只是访问权限不同
        prefs = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    /**
     * 判断SD卡是否存在
     *
     * @return
     */
    public static boolean isSDCardExist() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.w("isSDCardExist", "isSDCardExist:" + true);
            return true;
        }
        Log.w("isSDCardExist", "isSDCardExist:" + false);
        return false;
    }

    /**
     * 获取存储目录的剩余空间 返回值单位 Byte
     *
     * @return
     */
    public static long getSDFreeSize() {
        File path;
        if (isSDCardExist()) {
            // 取得SD卡文件路径
            path = Environment.getExternalStorageDirectory();
        } else {
            path = MyApplication.getMyAppliaction().getFilesDir();
        }
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        // 空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        // 返回SD卡空闲大小
        long size = freeBlocks * blockSize;
        Log.w("isSDCardExist", "剩余空间大小:" + size);
        return size; // 单位Byte
        // return (freeBlocks * blockSize)/1024; //单位KB
        // return (freeBlocks * blockSize)/1024 /1024; //单位MB

    }

    /**
     * 根据键字符串，存储一个字符串值
     *
     * @param key
     * @param value
     * @return 返回提交是否成功
     */
    public boolean putString(String key, String value) {
        if (TextUtils.isEmpty(key)) {
            return false;
        }

        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * 根据key值得到存储结果，如果没有找到value就返回null
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        return prefs.getString(key, null);
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
        if (TextUtils.isEmpty(key) || value == -1) {
            return false;
        }
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * 根据key值得到存储结果，如果没有找到value就返回-1
     *
     * @param key
     * @return
     */
    public int getInt(String key) {
        return prefs.getInt(key, -1);
    }

    /**
     * 根据键字符串，存储一个布尔值
     *
     * @param key
     * @param value
     * @return 返回提交是否成功
     */
    public boolean putBoolean(String key, boolean value) {
        if (TextUtils.isEmpty(key)) {
            return false;
        }
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * 根据key值得到存储结果，如果没有找到value就返回false
     *
     * @param key
     * @return
     */
    public boolean getBoolean(String key) {
        return prefs.getBoolean(key, false);
    }

    /**
     * 根据key值移除对应的数据
     *
     * @param key
     * @return
     */
    public boolean remove(String key) {
        editor.remove(key);
        return editor.commit();
    }

    /**
     * 清空少量的数据
     *
     * @return
     */
    public boolean clearLittleData() {
        editor.clear();
        return editor.commit();
    }

    // 注意拓展多用户登录
    /**
     * 存储登录时 的个人信息，以实现记住最后一次登录用户的效果
     *
     * @param context
     *            上下文对象
     * @param headMsg
     *            头部信息，区分是账号登录还是第三方登录
     * @param accountInforma
     *            这里包含账号登录时,存储账号名和密码;第三方登录时,存储thirdIdentify和openId
     * @param imgUrl
     *            头像url
     * @param username
     *            用户名
     * @return
     */
    public static boolean savePersonData(Context context, String headMsg,
                                         String accountInforma, String imgUrl, String username) {
        // 考虑到注册账号的特殊性，除了登陆是必要的账号密码，其他信息都可为空，但是也需要做相关处理
        // || imgUrl == null || username == null || headMsg == null
        if (context == null || accountInforma == null) {
            Log.i("savePersonData", "message is null");
            return false;
        }

        File file = new File(context.getFilesDir(), "ledonPerson.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
                Log.i("savePersonData", "error in file");
                return false;
            }
        }

        if (!file.exists()) {
            return false;
        } else {
            try {
                // 如果存在之前的账户信息,应该删除掉
                clearData(file.getAbsolutePath());
                /** 以读写方式建立一个RandomAccessFile对象 */
                RandomAccessFile raf = new RandomAccessFile(file, "rws");
                Log.i("savePersonData",
                        "RandomAccessFile文件指针的初始位置:" + raf.getFilePointer());
                // 将记录指针移动到文件最前
                raf.seek(0);// 确保是最后一次登录用户 如果是多用户登录，此处应去掉
                raf.write((headMsg + "\r\n").getBytes("utf-8"));// 头部信息，区分是账号登录还是第三方登录
                // 注意会出现乱码
                raf.write((accountInforma + "\r\n").getBytes("utf-8")); // 这里包含账号登录时,存储账号名和密码;第三方登录时,存储thirdIdentify和openId
                // 注意会出现乱码
                raf.write((imgUrl + "\r\n").getBytes("utf-8")); // 注意会出现乱码
                if (!TextUtils.isEmpty(username)) {
                    raf.write((username + "\r\n").getBytes("utf-8")); // 注意会出现乱码
                } else {
                    raf.write(("上一次登录用户" + "\r\n").getBytes("utf-8")); // 注意会出现乱码
                }
                raf.close();
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("savePersonData", "error in write");
                return false;
            }
            Log.i("savePersonData", "Success");
            return true;
        }
    }

    /**
     * 更新用户信息
     *
     * @param context
     * @param imgUrl
     * @param username
     * @return
     */
    public static boolean updatePersonData(Context context, String imgUrl,
                                           String username) {
        if (context == null || TextUtils.isEmpty(imgUrl)) {
            Log.i("updatePersonData", "message is null");
            return false;
        }

        File file = new File(context.getFilesDir(), "ledonPerson.txt");
        List<String> personMsg = new ArrayList<String>();
        if (file.exists()) {
            /** 以读写方式建立一个RandomAccessFile对象 */
            try {
                RandomAccessFile raf = new RandomAccessFile(file, "rws");
                Log.i("updatePersonData",
                        "RandomAccessFile文件指针的初始位置:" + raf.getFilePointer());
                // 将记录指针移动到文件最前
                raf.seek(0);

                byte[] buff = new byte[1024];
                int hasRead = 0;
                String data = null;
                while ((hasRead = raf.read(buff)) > 0) {
                    // 打印读取的内容，并将字节转为字符串输入 存在乱码问题
                    String str = new String(buff, 0, hasRead);
                    Log.i("updatePersonData", "personData:" + str);
                    data = str;// 如果是多用户登录,此处得加信息判断
                }

                Log.i("updatePersonData",
                        "RandomAccessFile文件指针的位置:" + raf.getFilePointer());

                String[] strArray = data.split("\r\n");
                for (int i = 0; i < strArray.length; i++) {
                    personMsg.add(strArray[i]);
                }
                strArray = null;
                Log.i("updatePersonData", "msg length:" + personMsg.size());
                personMsg.set(personMsg.size() - 2, imgUrl);
                personMsg.set(personMsg.size() - 1, username);

                clearData(file.getAbsolutePath());
                // 将记录指针移动到文件最前
                raf.seek(0);// 确保是最后一次登录用户 如果是多用户登录，此处应去掉

                for (int i = 0; i < personMsg.size(); i++) {
                    if (i != personMsg.size() - 1) {
                        raf.write((personMsg.get(i) + "\r\n").getBytes("utf-8"));
                    } else {
                        if (!TextUtils.isEmpty(personMsg.get(i))) {
                            raf.write((personMsg.get(i) + "\r\n")
                                    .getBytes("utf-8")); // 注意会出现乱码
                        } else {
                            raf.write(("上一次登录用户" + "\r\n").getBytes("utf-8")); // 注意会出现乱码
                        }
                    }
                }
                /*
                 * raf.write((headMsg + "\r\n").getBytes("utf-8"));//
                 * 头部信息，区分是账号登录还是第三方登录 // 注意会出现乱码 raf.write((accountInforma +
                 * "\r\n").getBytes("utf-8")); //
                 * 这里包含账号登录时,存储账号名和密码;第三方登录时,存储thirdIdentify和openId // 注意会出现乱码
                 * raf.write((imgUrl + "\r\n").getBytes("utf-8")); // 注意会出现乱码 if
                 * (!TextUtils.isEmpty(username)) { raf.write((username +
                 * "\r\n").getBytes("utf-8")); // 注意会出现乱码 } else {
                 * raf.write(("上一次登录用户" + "\r\n").getBytes("utf-8")); // 注意会出现乱码
                 * }
                 */

                raf.close();

            } catch (Exception e) {
                e.printStackTrace();
                Log.i("updatePersonData", "error in write");
                return false;
            }

        }
        Log.i("updatePersonData", "Success");
        return true;
    }

    /**
     * 清空数据
     *
     * @param path
     */
    public static void clearData(String path) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(
                    new File(path)));

            bw.write("");
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.i("savePersonData", "clearData error");
            e.printStackTrace();
        }

    }

    /**
     * 获取最后一次登录账户的信息
     *
     * @param context
     *            上下文对象
     * @return
     */
    public static List<String> getPersonData(Context context) {
        File file = new File(context.getFilesDir(), "ledonPerson.txt");
        List<String> personMsg = new ArrayList<String>();
        if (file.exists()) {

            try {
                /** 以读写方式建立一个RandomAccessFile对象 */
                RandomAccessFile raf = new RandomAccessFile(file, "rws");
                Log.i("getPersonData",
                        "RandomAccessFile文件指针的初始位置:" + raf.getFilePointer());
                // 将记录指针移动到文件最前
                raf.seek(0);

                byte[] buff = new byte[1024];
                int hasRead = 0;
                String data = null;
                while ((hasRead = raf.read(buff)) > 0) {
                    // 打印读取的内容，并将字节转为字符串输入 存在乱码问题
                    String str = new String(buff, 0, hasRead);
                    Log.i("getPersonData", "personData:" + str);
                    data = str;// 如果是多用户登录,此处得加信息判断
                }
                raf.close();

                String[] strArray = data.split("\r\n");
                for (int i = 0; i < strArray.length; i++) {
                    // Log.i("getPersonData", "strArray :" + strArray[i]);
                    personMsg.add(strArray[i]);
                }
                strArray = null;
                Log.i("getPersonData", "msg length:" + personMsg.size());
                return personMsg;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.i("getPersonData", "error");
                return null;
            }

        }

        Log.i("getPersonData", "file isn't exist");
        return null;

    }

    // 大文件存储，只适应从网络download数据下来，但是网络请求封装已经借由第三方框架实现了

    /**
     * 清除文件 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     *
     * @param directory
     *            对应文件夹
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    /**
     * 清除Cache
     *
     * @param context
     *            上下文对象
     */
    public static void clearCache(Context context) {
        // 内部缓存
        deleteFilesByDirectory(context.getCacheDir());
        if (isSDCardExist()) {
            // 外部cache
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }
}
