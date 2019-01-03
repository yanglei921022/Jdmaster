package com.thzy.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thzy.myapplication.base.BaseActivity;

public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}
