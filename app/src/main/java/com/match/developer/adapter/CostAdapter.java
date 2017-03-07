package com.match.developer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.match.developer.R;
import com.match.developer.model.Cost;

import java.util.List;

/**
 * Created by Tanner on 2017/3/2.
 */

public class CostAdapter extends BaseAdapter {
    List<Cost> mData;
    Context mContext;

    public CostAdapter(Context context, List<Cost> data) {
        this.mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Cost getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            holder = new Holder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_cost, viewGroup, false);
            holder.mTime = (TextView) view.findViewById(R.id.id_item_cost_time);
            holder.mMoney = (TextView) view.findViewById(R.id.id_item_cost_money);
            holder.mName = (TextView) view.findViewById(R.id.id_item_cost_name);
            holder.mDesc = (TextView) view.findViewById(R.id.id_item_cost_desc);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        Cost cost = mData.get(i);
        holder.mTime.setText(cost.getDate());
        if (cost.getType().toLowerCase().equals("in")) {
            holder.mMoney.setText("+" + cost.getMoney());
            holder.mMoney.setTextColor(mContext.getResources().getColor(R.color.green));
        } else if (cost.getType().toLowerCase().equals("out")) {
            holder.mMoney.setText("-" + cost.getMoney());
            holder.mMoney.setTextColor(mContext.getResources().getColor(R.color.red));
        }
        holder.mName.setText(cost.getName());
        holder.mDesc.setText(cost.getDesc());
        return view;
    }

    class Holder {
        TextView mTime;
        TextView mMoney;
        TextView mName;
        TextView mDesc;
    }
}
