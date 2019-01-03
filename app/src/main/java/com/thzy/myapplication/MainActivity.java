package com.thzy.myapplication;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thzy.myapplication.base.BaseActivity;
import com.thzy.myapplication.fragment.HomeFragment;
import com.thzy.myapplication.fragment.ListFragment;
import com.thzy.myapplication.fragment.MyFragment;
import com.thzy.myapplication.fragment.ProgressFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.fl_container)
    FrameLayout flContainer;
    @InjectView(R.id.fl_home)
    FrameLayout flHome;
    @InjectView(R.id.fl_zixun)
    FrameLayout flZixun;
    @InjectView(R.id.iv_kefu)
    ImageView ivKefu;
    @InjectView(R.id.tv_kefu)
    TextView tvKefu;
    @InjectView(R.id.fl_kefu)
    FrameLayout flKefu;
    @InjectView(R.id.fl_my)
    FrameLayout flMy;
    @InjectView(R.id.ll_main_tab)
    LinearLayout llMainTab;
    HomeFragment homeFragment;
    ListFragment listFragment;
    ProgressFragment progressFragment;
    MyFragment myFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        setListener();

        onClickListener.onClick(llMainTab.getChildAt(0));
    }
    public void setTabSelection(int index) {

        // 开启一个Fragment事务
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // 先隐藏掉所有的Fragment，
        hideFragments(transaction);
        switch (index) {


            case 0:

                if (progressFragment == null) {
                    progressFragment = new ProgressFragment();
                    transaction.add(R.id.fl_container, progressFragment);

                } else {
                    transaction.show(progressFragment);
                }
                break;
           /* case 3:

                if (chaoShiFragment == null) {
                    chaoShiFragment = new ChaoShiFragment();
                    transaction.add(R.id.fl_container, chaoShiFragment);

                } else {
                    transaction.show(chaoShiFragment);
                }
                break;*/
            case 2:

                if (listFragment == null) {
                    listFragment = new ListFragment();
                    transaction.add(R.id.fl_container, listFragment);

                } else {
                    transaction.show(listFragment);
                }
                break;
            case 1:

                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fl_container, homeFragment);

                } else {
                    transaction.show(homeFragment);
                }
                break;
            case 3:

                if (myFragment == null) {
                    myFragment = new MyFragment();
                    transaction.add(R.id.fl_container, myFragment);

                } else {
                    transaction.show(myFragment);
                }
                break;

        }
        transaction.commit();
    }

    /**
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
       /* if (homeFragment != null) {
            transaction.hide(homeFragment);
        }*/
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (listFragment != null) {
            transaction.hide(listFragment);
        }
        /*if (chaoShiFragment != null) {
            transaction.hide(chaoShiFragment);
        }*/
        if (progressFragment != null) {
            transaction.hide(progressFragment);
        }
        if (myFragment != null) {
            transaction.hide(myFragment);
        }


    }

    private void setListener() {
        //所有孩子的数量
        int childCount = llMainTab.getChildCount();
        System.out.println("孩子的数量+" + childCount);
        for (int i = 0; i < childCount; i++) {
            FrameLayout childAt = (FrameLayout) llMainTab.getChildAt(i);
            childAt.setOnClickListener(onClickListener);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = llMainTab.indexOfChild(v);
            //修改底部tab的ui
            changeUi(index);
            changeFragment(index);
        }
    };

    private void changeFragment(int index) {
        if (index == 0) {
            setTabSelection(0);
        } else if (index == 1) {
            setTabSelection(1);
        } else if (index == 2) {
            setTabSelection(2);
        } else if (index == 3) {
            setTabSelection(3);
        }/* else if (index == 4) {
            setTabSelection(4);
        }*/
    }

    /**
     * 将每个item的控件一起改变状态
     *
     * @param item
     * @param b
     */
    private void setEnable(View item, boolean b) {
        item.setEnabled(b);
        if (item instanceof ViewGroup) {
            int childCount = ((ViewGroup) item).getChildCount();
            for (int i = 0; i < childCount; i++) {
                setEnable(((ViewGroup) item).getChildAt(i), b);
            }
        }
    }

    /**
     * 修改底部tab的按键
     * g改变index对应的孩子的状态 包括这个孩子控件中的多有控件的状态（可用 不可用）
     *
     * @param index
     */
    public void changeUi(int index) {
        //Toast.makeText(this,index+"",Toast.LENGTH_SHORT).show();
        int childCount = llMainTab.getChildCount();
        for (int i = 0; i < childCount; i++) {
            //判断i是否与index相同
            //相同不可用状态enable=FALSE
            if (i == index) {
                llMainTab.getChildAt(i).setEnabled(false);
                //每一个item控件都需要切换状态
                setEnable(llMainTab.getChildAt(i), false);
            } else {
                llMainTab.getChildAt(i).setEnabled(true);
                //每一个item控件都需要切换状态
                setEnable(llMainTab.getChildAt(i), true);
            }
        }
    }

    @OnClick({R.id.fl_home, R.id.fl_zixun, R.id.fl_kefu, R.id.fl_my})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_home:
                setTabSelection(0);
                break;
            case R.id.fl_zixun:
                setTabSelection(1);
                break;
            case R.id.fl_kefu:
                setTabSelection(2);
                break;
            case R.id.fl_my:
                setTabSelection(3);
                break;
        }
    }
    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //当两次时间差值大于2000的时候说明是第一次按下back键，这时候提示，当按下后两次时间差小于2000的时候退出
            if (System.currentTimeMillis() - mExitTime > 2000) {

                toast("再按一次退出程序");
                //记录第一次按下的时间
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onResume() {
        super.onResume();
        //initview();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("onRestart");
    }

    public void onPause() {
        super.onPause();
    }

}
