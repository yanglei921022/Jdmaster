package com.thzy.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.thzy.myapplication.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @InjectView(R.id.iv)
    ImageView iv;
    @InjectView(R.id.et_count)
    EditText etCount;
    @InjectView(R.id.et_pwd)
    EditText etPwd;
    @InjectView(R.id.checkbox)
    CheckBox checkbox;
    @InjectView(R.id.bt_login)
    Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        final SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sp.edit().putBoolean("isLogin",true).commit();
                   // putBoolean("isLogin",true);
                }else {
                    sp.edit().putBoolean("isLogin",false).commit();
                }
            }
        });
    }

    @OnClick(R.id.bt_login)
    public void onViewClicked() {
        String count = etCount.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        if (!TextUtils.isEmpty(count)&&!TextUtils.isEmpty(pwd)){
            activityPageChange(MainActivity.class,null,true);
        }else {
            toast("请输入账号密码！");
        }
    }
}
