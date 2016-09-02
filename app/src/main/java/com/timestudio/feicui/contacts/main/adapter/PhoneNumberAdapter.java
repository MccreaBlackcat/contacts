package com.timestudio.feicui.contacts.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.timestudio.feicui.contacts.R;
import com.timestudio.feicui.contacts.main.entity.PhoneNumberEntity;

import java.util.ArrayList;

/**
 * @author  Created by ShenGqiang on 2016/9/2.
 * @description  电话名字和号码的适配器
 */
public class PhoneNumberAdapter extends BaseAdapter {
    //实例化一个存储PhoneNumberEntity对象的 ArrayList
    private ArrayList<PhoneNumberEntity> mData = new ArrayList<>();
    //声明一个布局加载器
    protected LayoutInflater inflater;

    public PhoneNumberAdapter(ArrayList<PhoneNumberEntity> mData, Context context) {
        this.mData = mData;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //声明一个viewHolder
        ViewHolder holder ;
        if(convertView==null){
            //使用布局加载器加载布局
            convertView = inflater.inflate(R.layout.item_pn_list,null);
            //保存ViewHolder的状态
            holder = new ViewHolder();
            holder.tv_phoneName = (TextView) convertView.findViewById(R.id.tv_phonename);
            holder.tv_phoneNumber = (TextView) convertView.findViewById(R.id.tv_phonenum);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        //从数据源中装载数据
        holder.tv_phoneName.setText(mData.get(position).getPhoneName());
        holder.tv_phoneNumber.setText(mData.get(position).getPhoneNumber());
        return convertView;
    }

    //优化适配器
    public class ViewHolder{
        TextView tv_phoneName;//电话名字
        TextView tv_phoneNumber;//电话号码

    }
}
