package com.thzy.myapplication.base;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thzy.myapplication.LoginActivity;
import com.thzy.myapplication.MainActivity;
import com.thzy.myapplication.R;

public class FlashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("isLogin",false);
        if (isLogin){
            activityPageChange(MainActivity.class,null,true);
        }else {
            activityPageChange(LoginActivity.class,null,true);
        }
    }
}
