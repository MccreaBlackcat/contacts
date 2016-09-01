package com.timestudio.feicui.contacts.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.timestudio.feicui.contacts.R;
import com.timestudio.feicui.contacts.main.entity.PhoneTypeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinkpad on 2016/8/26.
 */
public class PhoneTypeAdapter extends BaseAdapter{
    List <PhoneTypeEntity> mData = new ArrayList<>();//存储了电话类型的type的集合
    protected LayoutInflater inflater;//声明一个布局加载器

    public PhoneTypeAdapter (Context context , ArrayList<PhoneTypeEntity> list) {
        this.mData = list;
        //获取布局
        inflater = LayoutInflater.from(context); //实例化布局加载器
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
        ViewHolder holder = null;
        if(convertView==null){
            //使用布局加载器加载布局
            convertView = inflater.inflate(R.layout.item_pt_list,null);
            //保存ViewHolder的状态
            holder = new ViewHolder();
            holder.tv_typeName = (TextView) convertView.findViewById(R.id.tv_typeName);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        //从数据源中装载数据
        holder.tv_typeName.setText(mData.get(position).getPhoneTypeName());
        return convertView;
    }

    //优化适配器
    public class ViewHolder{

        TextView tv_typeName;//电话类型
        ImageView im_arrowRight;//箭头向右
    }
}
