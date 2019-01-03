package com.thzy.myapplication.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.thzy.myapplication.R;
import com.thzy.myapplication.adapter.ConstellationAdapter;
import com.thzy.myapplication.adapter.GirdDropDownAdapter;
import com.thzy.myapplication.adapter.ListDropDownAdapter;
import com.thzy.myapplication.adapter.XiaKuanLvAdapter;
import com.thzy.myapplication.data.Json;
import com.thzy.myapplication.utils.ShowActive;
import com.thzy.myapplication.utils.ShowCity;
import com.thzy.myapplication.utils.ShowPup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressFragment extends Fragment {


    @InjectView(R.id.tv_one)
    TextView tvOne;
    @InjectView(R.id.tv_two)
    TextView tvTwo;
    @InjectView(R.id.tv_three)
    TextView tvThree;
    @InjectView(R.id.tv_final)
    TextView tvFinal;
    @InjectView(R.id.main_right_drawer_layout)
    RelativeLayout mainRightDrawerLayout;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @InjectView(R.id.tv_meiti)
    TextView tvMeiti;
    @InjectView(R.id.gv_meiti)
    GridView gvMeiti;
    @InjectView(R.id.tv_city)
    TextView tvCity;
    @InjectView(R.id.tv_city_sure)
    TextView tvCitySure;
    @InjectView(R.id.tv_active)
    TextView tvActive;
    @InjectView(R.id.lv_name)
    ListView lvName;
    @InjectView(R.id.tv_time)
    TextView tvTime;
    @InjectView(R.id.ll)
    LinearLayout ll;
    @InjectView(R.id.bt_one)
    Button btOne;
    @InjectView(R.id.bt_two)
    Button btTwo;
    private String constellations[] = {"公交车站", "公交车身", "社区广告", "地铁灯箱"};
    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private String names[] = {"可乐测试活动", "淘宝特卖活动"};
    private List<String> oneList = new ArrayList<>();
    private List<String> twoList = new ArrayList<>();
    private List<String> threeList = new ArrayList<>();
    RecyclerView mRecyclerView;
    //支持下拉刷新的ViewGroup
    private PtrClassicFrameLayout mPtrFrame;
    //List数据
    private List<Json> title = new ArrayList<>();
    //RecyclerView自定义Adapter
    private XiaKuanLvAdapter adapter;
    //添加Header和Footer的封装类
    private RecyclerAdapterWithHF mAdapter;
    private final static String TAG = "hyh";
    private SharedPreferences sp;
    private String header;
    private String vn;
    private String aid;
    private String body[] = {"徐锦江撞脸圣诞老人", "刘强东向妻子道歉", "赵薇被质疑新加坡人", "美国最惨淡圣诞节", "裁判又抢镜！阿联乌龙球引争议", "杜峰不满，周鹏这一动作成亮点！"};
    private ConstellationAdapter gvadpter;
    private ListDropDownAdapter lvNameAdapter;

    public ProgressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        ButterKnife.inject(this, view);
        mRecyclerView = view.findViewById(R.id.rv_lixi);
        mPtrFrame = view.findViewById(R.id.rotate_header_list_view_frame);
        initEvent();
        return view;
    }

    private int constellationPosition = 0;

    private void initEvent() {


        sp = getActivity().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        aid = sp.getString("Aid", null);
        String sign = sp.getString("sign", null);
        String pin = sp.getString("pin", null);
        vn = sp.getString("versionName", "1.0");
        header = pin + "^^" + sign + "^^";
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        /*adapter = new NewKouZiAdapter(getActivity(), title);
        mAdapter = new RecyclerAdapterWithHF(adapter);
        mRecyclerView.setAdapter(mAdapter);*/
        if (adapter == null) {
            adapter = new XiaKuanLvAdapter(getActivity(), title, aid);
            mAdapter = new RecyclerAdapterWithHF(adapter);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            adapter.notifyDataSetChanged();
        }

        //下拉刷新支持时间
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        //下拉刷新一些设置 详情参考文档
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        //进入Activity就进行自动下拉刷新
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);
        //下拉刷新

        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                title.clear();

                for (int i = 0; i < body.length; i++) {
                    String s = body[i];
                    Json json = new Json(s);
                    title.add(json);
                }


                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                        mPtrFrame.refreshComplete();
                        mPtrFrame.setLoadMoreEnable(true);
                    }
                }, 1000);

            }
        });
        //上拉加载
        mPtrFrame.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                for (int i = 0; i < constellations.length; i++) {
                    String s = constellations[i];
                    Json json = new Json(s);
                    title.add(json);
                }
                // postJsontoNet(index);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //模拟数据
                        mAdapter.notifyDataSetChanged();
                        mPtrFrame.loadMoreComplete(true);
                    }
                }, 1000);

            }
        });
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        for (int i = 0; i < constellations.length; i++) {
            oneList.add(constellations[i]);
        }
        for (int i = 0; i < citys.length; i++) {
            twoList.add(citys[i]);
        }
        for (int i = 0; i < names.length; i++) {
            threeList.add(names[i]);
        }
        gvadpter = new ConstellationAdapter(getActivity(), oneList);
        gvMeiti.setAdapter(gvadpter);
        gvMeiti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gvadpter.setCheckItem(position);
                constellationPosition = position;
                tvOne.setText(oneList.get(position));
            }
        });
        lvNameAdapter = new ListDropDownAdapter(getActivity(), threeList);
        lvName.setAdapter(lvNameAdapter);
        lvName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lvNameAdapter.setCheckItem(position);
                constellationPosition = position;
                tvThree.setText(threeList.get(position));
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //清空集合

                    //刷新适配器
                    adapter.notifyDataSetChanged();
                    //获得消息数据
                    String text = (String) msg.obj;
                    //解析json串
                    // tojson(text);
                    processNetData(text);
                    break;
                case 1:
                    adapter.notifyDataSetChanged();
                    //获得消息数据
                    //解析json串
                    // tojson(text);
                    processNetData();
                    break;
                case 2:
                    //获得消息数据
                    int pos = (int) msg.obj;
                    //解析json串
                    // tojson(text);
                    postData(pos);
                    break;
                case 3:
                    //获得消息数据
                    String city = (String) msg.obj;
                    tvCitySure.setText(city);
                    break;
                case 4:
                    //获得消息数据
                    int num = (int) msg.obj;
                    lvNameAdapter.setCheckItem(num);
                    lvNameAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    private void postData(int pos) {
        gvadpter.setCheckItem(pos);
        gvadpter.notifyDataSetChanged();
    }

    private void processNetData() {
        title.clear();
        for (int i = 0; i < threeList.size(); i++) {
            String s = oneList.get(i);
            Json json = new Json(s);
            title.add(json);
        }
        mAdapter.notifyDataSetChanged();
        mPtrFrame.refreshComplete();
        mPtrFrame.setLoadMoreEnable(true);
    }

    private void processNetData(String text) {
        title.clear();
        for (int i = 0; i < oneList.size(); i++) {
            String s = oneList.get(i);
            Json json = new Json(s);
            title.add(json);
        }
        mAdapter.notifyDataSetChanged();
        mPtrFrame.refreshComplete();
        mPtrFrame.setLoadMoreEnable(true);
    }

    private void getData() {

        Message message = new Message();
        message.what = 0;
        message.obj = "asasa";
        //给handler发送消息
        handler.sendMessage(message);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.tv_one, R.id.tv_two, R.id.tv_three, R.id.tv_final, R.id.bt_one, R.id.bt_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_one:
              /*  ShowPup showPup = new ShowPup(getActivity(), tvOne, oneList);
                showPup.bottomwindow(view);*/
               /* tvOne.setBackgroundColor(Color.parseColor("#ffffff"));
                tvTwo.setBackgroundColor(Color.parseColor("#99cccccc"));
                tvThree.setBackgroundColor(Color.parseColor("#99cccccc"));*/
                tvOne.setBackgroundResource(R.drawable.text_bg);
                tvTwo.setBackgroundResource(R.drawable.text_di);
                tvThree.setBackgroundResource(R.drawable.text_di);
                tvFinal.setBackgroundResource(R.drawable.text_di);
                showOne(view);
                break;
            case R.id.tv_two:
           /*     tvOne.setBackgroundColor(Color.parseColor("#99cccccc"));
                tvTwo.setBackgroundColor(Color.parseColor("#ffffff"));
                tvThree.setBackgroundColor(Color.parseColor("#99cccccc"));*/
              /*  ShowCity showCity = new ShowCity(getActivity(), tvTwo, twoList);
                showCity.bottomwindow(view);
                String text = tvTwo.getText().toString();
                if (!text.equals("城市")) {
                    Message message=new Message();
                    message.what=3;
                    message.obj=text;
                    //给handler发送消息
                    handler.sendMessage(message);
                }*/
                tvOne.setBackgroundResource(R.drawable.text_di);
                tvTwo.setBackgroundResource(R.drawable.text_bg);
                tvThree.setBackgroundResource(R.drawable.text_di);
                tvFinal.setBackgroundResource(R.drawable.text_di);
                showCity(view);
                break;
            case R.id.tv_three:
                tvOne.setBackgroundResource(R.drawable.text_di);
                tvTwo.setBackgroundResource(R.drawable.text_di);
                tvThree.setBackgroundResource(R.drawable.text_bg);
                tvFinal.setBackgroundResource(R.drawable.text_di);
                /*tvOne.setBackgroundColor(Color.parseColor("#99cccccc"));
                tvTwo.setBackgroundColor(Color.parseColor("#99cccccc"));
                tvThree.setBackgroundColor(Color.parseColor("#ffffff"));*/
              /*  ShowActive showActive = new ShowActive(getActivity(), tvThree, threeList);
                showActive.bottomwindow(view);*/
                showActive(view);
                break;
            case R.id.tv_final:
                drawerLayout.openDrawer(Gravity.RIGHT);
                break;
            case R.id.bt_one:
                gvadpter.setCheckItem(0);
                lvNameAdapter.setCheckItem(0);
                tvOne.setText(oneList.get(0));
                tvThree.setText(threeList.get(0));
                //drawerLayout.closeDrawers();
                break;
            case R.id.bt_two:
                drawerLayout.closeDrawers();
                getDataTwo();
                break;
        }
    }

    int activeNum = 0;

    private void showActive(View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.pup_three, null);
        popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, 400);
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
        setButtonListenersActive(layout);
        //添加pop窗口关闭事件，主要是实现关闭时改变背景的透明度
        popupWindow.setOnDismissListener(new poponDismissListeneractive());
    }

    private void setButtonListenersActive(RelativeLayout layout) {
        ListView gv = layout.findViewById(R.id.lv_two);
        final ListDropDownAdapter listDropDownAdapter = new ListDropDownAdapter(getActivity(), threeList);
        gv.setAdapter(listDropDownAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvThree.setText(threeList.get(position));
                listDropDownAdapter.setCheckItem(position);
                activeNum=position;
                Message message = new Message();
                message.what = 4;
                message.obj = position;
                //给handler发送消息
                handler.sendMessage(message);
                //  popupWindow.dismiss();
            }
        });
        Button reset = layout.findViewById(R.id.bt_reset);
        Button sure = layout.findViewById(R.id.bt_sure);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDropDownAdapter.setCheckItem(0);
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvThree.setText(threeList.get(activeNum));
                popupWindow.dismiss();
             //   tvThree.setBackgroundColor(Color.parseColor("#99cccccc"));
                //getData();
            }
        });
    }

    private void showCity(View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.pup_two, null);
        popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, 400);
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
        setButtonListenerscity(layout);
        //添加pop窗口关闭事件，主要是实现关闭时改变背景的透明度
        popupWindow.setOnDismissListener(new poponDismissListenercity());
        //popupWindow.setOutsideTouchable(true);
        //backgroundAlpha(0.5f);
    }

    private void setButtonListenerscity(RelativeLayout layout) {
        ListView gv = layout.findViewById(R.id.lv);
        GirdDropDownAdapter girdDropDownAdapter = new GirdDropDownAdapter(getActivity(), twoList);
        gv.setAdapter(girdDropDownAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = twoList.get(position);
                tvTwo.setText(text);
                Message message = new Message();
                message.what = 3;
                message.obj = text;
                //给handler发送消息
                handler.sendMessage(message);
             //   tvTwo.setBackgroundColor(Color.parseColor("#99cccccc"));
                popupWindow.dismiss();
            }
        });
    }

    private void getDataTwo() {
        Message message = new Message();
        message.what = 1;
        message.obj = "asasa";
        //给handler发送消息
        handler.sendMessage(message);
    }

    PopupWindow popupWindow;

    private void showOne(View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.pup_one, null);
        popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, 400);
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
        // popupWindow.setOutsideTouchable(true);
        //backgroundAlpha(0.5f);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
    //  private String constellations[] = {"公交车站", "公交车身", "社区广告", "地铁灯箱"};

    private void setButtonListeners(RelativeLayout layout) {
        GridView gv = layout.findViewById(R.id.gv);
        Button reset = layout.findViewById(R.id.bt_reset);
        Button sure = layout.findViewById(R.id.bt_sure);
        final ConstellationAdapter constellationAdapter = new ConstellationAdapter(getActivity(), oneList);

        gv.setAdapter(constellationAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = oneList.get(position);
                constellationAdapter.setCheckItem(position);

                constellationPosition = position;
                Message message = new Message();
                message.what = 2;
                message.obj = position;
                //给handler发送消息
                handler.sendMessage(message);


            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constellationAdapter.setCheckItem(0);
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvOne.setText(oneList.get(constellationPosition));
                popupWindow.dismiss();
               // tvOne.setBackgroundColor(Color.parseColor("#99cccccc"));
                getData();
            }
        });
    }

    class poponDismissListener implements PopupWindow.OnDismissListener {


        @Override
        public void onDismiss() {
           // tvOne.setBackgroundColor(Color.parseColor("#99cccccc"));
        /*    setTextColor(context.getResources().getColor(R.color.drop_down_selected));
            viewHolder.mText.setBackgroundResource(R.drawable.check_bg);*/
            tvOne.setBackgroundResource(R.drawable.text_umcolor);
            tvTwo.setBackgroundResource(R.drawable.text_umcolor);
            tvThree.setBackgroundResource(R.drawable.text_umcolor);
            tvFinal.setBackgroundResource(R.drawable.text_umcolor);
            backgroundAlpha(1.0f);
        }
    }
    class poponDismissListenercity implements PopupWindow.OnDismissListener {


        @Override
        public void onDismiss() {
            //tvTwo.setBackgroundColor(Color.parseColor("#99cccccc"));
            tvOne.setBackgroundResource(R.drawable.text_umcolor);
            tvTwo.setBackgroundResource(R.drawable.text_umcolor);
            tvThree.setBackgroundResource(R.drawable.text_umcolor);
            tvFinal.setBackgroundResource(R.drawable.text_umcolor);
            backgroundAlpha(1.0f);
        }
    }
    class poponDismissListeneractive implements PopupWindow.OnDismissListener {


        @Override
        public void onDismiss() {
           // tvThree.setBackgroundColor(Color.parseColor("#99cccccc"));
            tvOne.setBackgroundResource(R.drawable.text_umcolor);
            tvTwo.setBackgroundResource(R.drawable.text_umcolor);
            tvThree.setBackgroundResource(R.drawable.text_umcolor);
            tvFinal.setBackgroundResource(R.drawable.text_umcolor);
            backgroundAlpha(1.0f);
        }
    }
}
