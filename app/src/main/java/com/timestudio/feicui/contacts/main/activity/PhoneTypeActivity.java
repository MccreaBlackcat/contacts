package com.timestudio.feicui.contacts.main.activity;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.timestudio.feicui.contacts.R;
import com.timestudio.feicui.contacts.main.adapter.PhoneTypeAdapter;
import com.timestudio.feicui.contacts.main.db.DatabaseOperation;
import com.timestudio.feicui.contacts.main.entity.PhoneTypeEntity;
import com.timestudio.feicui.contacts.main.entry.PhoneTypeEntry;

import java.util.ArrayList;

import static com.timestudio.feicui.contacts.main.db.DatabaseOperation.READ_DB_TYPE;

/**
 * @author create by ShenGqiang on 2016/8/26
 * @description 显示电话号码类型的界面
 */
public class PhoneTypeActivity extends BaseActivity {
    //电话类型列表
    ListView lv_phoneType;
    //数据还在加载时，显示的布局
    LinearLayout ll_loading;
    //声明一个电话类型适配器
    PhoneTypeAdapter adapter;
    //是否进入双击退出计时状态
    boolean isExit = false;
    //第一次点击退出的时间戳
    long l_firstClickTime;
    //第二次点击退出的时间戳
    long l_secondClickTime;
    //声明数据集合
    ArrayList<PhoneTypeEntity> mData;
    @Override
    protected int setContent() {
        return R.layout.activity_phonetype;
    }

    @Override
    protected void initView() {
        lv_phoneType = (ListView)findViewById(R.id.lv_phoneType);
        ll_loading = (LinearLayout)findViewById(R.id.ll_loading);
        new InitTask().execute();
    }

    @Override
    protected void initListener() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //双击退出
        doubleClickExit(keyCode,event);
        return true;
    }

    /**
     * @description 初始电话类型列表
     */
    protected void initList(){
        mData = new ArrayList<>();
        //声明一个手机类型对象
        PhoneTypeEntity entity;
        //引入数据库
        DatabaseOperation.importDatabase(PhoneTypeActivity.this);
        //读取数据
        Cursor cursor = DatabaseOperation.readDatabase(READ_DB_TYPE,null);
        //将游标移到第一位
        cursor.moveToFirst();
        //遍历游标，向手机类型数据集合mData中添加数据
        do {
            //获取到游标中电话类型数据，并记录
            String phoneType = cursor.getString(cursor.getColumnIndexOrThrow(PhoneTypeEntry.COLUMNS_NAME_TYPE));
            //获取到游标中子表类型数据，并记录
            String subtable = cursor.getString(cursor.getColumnIndexOrThrow(PhoneTypeEntry.COLUMNS_NAME_SUBTABLE));
            //手机类型实体对象实例化
            entity = new PhoneTypeEntity(phoneType,subtable);
            //将实例化后的对象添加到mData中
            mData.add(entity);
        }while (cursor.moveToNext());
    }
    /**
     * @description 双击退出程序的函数
     */

    private void doubleClickExit(int keyCode,KeyEvent event){
        //当用户第一次点击返回按钮时
        if(keyCode == KeyEvent.KEYCODE_BACK && isExit == false){
            isExit = true;//设置记录标志为true
            l_firstClickTime = System.currentTimeMillis();//获取第一次点击退出键的时间
            //显示再次点击退出提示
            Toast.makeText(this,getString(R.string.exit_toast),Toast.LENGTH_SHORT).show();
        }
        //用户第二次点击返回键
        else if(keyCode == KeyEvent.KEYCODE_BACK && isExit == true){
            l_secondClickTime = System.currentTimeMillis();//记录第二次点击退出的时间
            //时间差在2秒内，退出程序
            if(( l_secondClickTime - l_firstClickTime ) < 2000){
                finish();
            }else {
                isExit = false; //重置记录退出标志
                doubleClickExit(keyCode,event); //超出2000ms时，重新开始逻辑函数
            }
        }
    }


    /**
     *  @Description  异步初始化操作类
     */
    class InitTask extends AsyncTask<Void, Void, Void> {

        //任务启动后在异步线程中执行的代码，不可操作UI
        @Override
        protected Void doInBackground(Void... params) {

            //加载数据列列表
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            initList();
            return null;
        }
        //任务启动之前执行的代码，可操作UI

        @Override
        protected void onPreExecute() {
            //让loading界面显示
            ll_loading.setVisibility(View.VISIBLE);
        }
        //任务完成之后执行的代码，可以操作UI

        @Override
        protected void onPostExecute(Void aVoid) {
            //隐藏界面
            ll_loading.setVisibility(View.GONE);
            //实例化适配器
            adapter = new PhoneTypeAdapter(PhoneTypeActivity.this, mData);

            //给listview设置监听
            lv_phoneType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent;
                    switch (position){
                        case 0:
                            //本地电话
                            intent = new Intent(Intent.ACTION_DIAL);
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);
                            }
                            break;
                        default:
                            //子表电话页面
                            intent = new Intent(PhoneTypeActivity.this,PhoneNumberActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("subtable",mData.get(position).getSubtable());
                            intent.putExtras(bundle);
                            startActivity(intent);
                            break;
                    }
                }
            });
            //给列表设置适配器
            lv_phoneType.setAdapter(adapter);
            //加载单个的item动画
            Animation rightin = AnimationUtils.loadAnimation(PhoneTypeActivity.this,R.anim.rightin);
            //根据单个的item动画创建布局过渡动画控制器
            LayoutAnimationController lc = new LayoutAnimationController(rightin);
            //设置动画延迟时间
            lc.setDelay(0.2f);
            //设置动画播放次序
            lc.setOrder(LayoutAnimationController.ORDER_NORMAL);//随机播放
            //给目标ListView设置item布局过渡动画
            lv_phoneType.setLayoutAnimation(lc);
        }
    }
}
