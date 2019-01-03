package com.thzy.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.thzy.myapplication.R;
import com.thzy.myapplication.data.Json;

import java.util.List;

/**
 * Created by sj on 2018/3/7.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<String> mTitle;
    private String  mAid;

    public MessageAdapter(Context context, List<String> title, String aid) {
        mContext = context;
        mTitle = title;
        mAid=aid;
        mLayoutInflater = LayoutInflater.from(context);
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView tv;
        TextView item_tv_money;
        TextView item_tv_date;
        TextView item_edu;
        TextView item_qixian;
        TextView item_tv_person;
        ImageView logo;
        ImageView fenlei;
        Button bt;

        public NormalViewHolder(View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.item_pic);
            fenlei = itemView.findViewById(R.id.item_fenlei);
            title = itemView.findViewById(R.id.item_title);
            tv = itemView.findViewById(R.id.item_tv);
            item_tv_money = itemView.findViewById(R.id.item_tv_money);
            item_tv_date = itemView.findViewById(R.id.item_tv_date);
            item_edu = itemView.findViewById(R.id.item_edu);
            item_qixian = itemView.findViewById(R.id.item_qixian);
            item_tv_person = itemView.findViewById(R.id.item_tv_person);
            bt = itemView.findViewById(R.id.item_button);

        }
    }

    //在该方法中我们创建一个ViewHolder并返回，ViewHolder必须有一个带有View的构造函数，这个View就是我们Item的根布局，在这里我们使用自定义Item的布局；
    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(mLayoutInflater.inflate(R.layout.item_xiakuanlv, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        NormalViewHolder viewholder = (NormalViewHolder) holder;
        // viewholder.mTextView.setText(mTitle.get(position));
        //  viewholder.mTextView.setText(position+"【下款口子】零钱巴士简单资料,最高额度 额度1300");
       /* XKlDataBean.DataBean kouziListBean = mTitle.get(position);
        String trim = kouziListBean.getTrait();
        if (TextUtils.isEmpty(trim)) {
            viewholder.fenlei.setVisibility(View.GONE);
        } else {
            Glide.with(mContext).load(trim).into(viewholder.fenlei);
        }
        viewholder.title.setText(kouziListBean.getName());
        Glide.with(mContext).load(kouziListBean.getLogo()).into(viewholder.logo);
        viewholder.tv.setText(kouziListBean.getAdvantage());
        viewholder.item_tv_money.setText(kouziListBean.getPosition());
        viewholder.item_tv_date.setText(kouziListBean.getDay());
        viewholder.item_edu.setText(kouziListBean.getEd());
        viewholder.item_qixian.setText(kouziListBean.getQx());
        viewholder.item_tv_person.setText(kouziListBean.getDayNum());
        viewholder.bt.setText(kouziListBean.getSq());
*/

    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mTitle == null ? 0 : mTitle.size();
    }
}

