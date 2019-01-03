package com.thzy.myapplication.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thzy.myapplication.R;
import com.thzy.myapplication.adapter.GirdDropDownAdapter;
import com.thzy.myapplication.adapter.ListDropDownAdapter;

import java.util.Arrays;
import java.util.List;


/**
 * Created by Administrator on 2017/12/11.
 */

public class ShowActive {
    private Activity getActivity;
    private TextView textView;
    private List<String> list;
    public ShowActive(Activity getActivity, TextView textView, List<String> list) {
        this.getActivity = getActivity;
        this.textView = textView;
        this.list=list;
    }

    PopupWindow popupWindow;
    private float alpha=0.5f;




    public void bottomwindow(View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        RelativeLayout layout = (RelativeLayout) getActivity.getLayoutInflater().inflate(R.layout.pup_three, null);
        popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, 600);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 添加弹出、弹入的动画
       // popupWindow.setAnimationStyle(R.style.AnimationTopFade);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
      //  popupWindow.showAtLocation(view, Gravity.LEFT | Gravity.TOP, 0, -location[1]);
        popupWindow.showAsDropDown(view);
        //添加按键事件监听
        setButtonListeners(layout);
        //添加pop窗口关闭事件，主要是实现关闭时改变背景的透明度
        popupWindow.setOnDismissListener(new poponDismissListener());
      //  popupWindow.setOutsideTouchable(true);
        //backgroundAlpha(0.5f);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        //0.0-1.0
        getActivity.getWindow().setAttributes(lp);
        getActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
  //  private String constellations[] = {"公交车站", "公交车身", "社区广告", "地铁灯箱"};

    private void setButtonListeners(RelativeLayout layout) {
        ListView gv = layout.findViewById(R.id.lv_two);
        ListDropDownAdapter listDropDownAdapter = new ListDropDownAdapter(getActivity, list);
        gv.setAdapter(listDropDownAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(list.get(position));
                popupWindow.dismiss();
            }
        });
    }

   class poponDismissListener implements PopupWindow.OnDismissListener{


        @Override
        public void onDismiss() {
            textView.setBackgroundColor(Color.parseColor("#99cccccc"));
            backgroundAlpha(1.0f);
        }
    }

}
