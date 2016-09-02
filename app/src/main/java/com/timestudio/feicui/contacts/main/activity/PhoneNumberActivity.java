package com.timestudio.feicui.contacts.main.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.timestudio.feicui.contacts.R;
import com.timestudio.feicui.contacts.main.adapter.PhoneNumberAdapter;
import com.timestudio.feicui.contacts.main.entity.PhoneNumberEntity;
import com.timestudio.feicui.contacts.main.entry.PhoneNumberEntry;

import java.util.ArrayList;

import static com.timestudio.feicui.contacts.main.db.DatabaseOperation.readDatabase;
import static com.timestudio.feicui.contacts.main.db.DatabaseOperation.READ_DB_PHONENUMBER;

/**
 * @author create by ShenGqiang on 2016/9/2
 * @description 显示不同类型电话号码内容的界面
 */
public class PhoneNumberActivity extends BaseActivity {
    //电话号码列表
    ListView lv_phonenumber;
    //数据加载时，显示的页面
    LinearLayout ll_loading;
    //声明一个电话号码适配器
    PhoneNumberAdapter adapter;
    //声明一个PhoneNumberEntity的Arraylist
    ArrayList<PhoneNumberEntity> mData ;
    //记录手机拨号权限
    boolean isOk = false;
    private static final int TAG_PERMISSION = 1023;
    //记录listView中的position
    int index;

    @Override
    protected int setContent() {
        return R.layout.activity_phone_number;
    }

    @Override
    protected void initView() {
        lv_phonenumber = (ListView)findViewById(R.id.lv_phonenumber);
        ll_loading = (LinearLayout)findViewById(R.id.ll_loading);
        new InitTask().execute();
    }

    @Override
    protected void initListener() {

    }

    /**
     *@description 读取数据，加载ListView
     */
    private void initList(){
        mData = new ArrayList<>();
        //声明一个电话号码实体对象
        PhoneNumberEntity entity;
        //获取上个界面传递过来的数据
        Bundle bundle = getIntent().getExtras();
        String type = bundle.get("subtable").toString();
        //读取数据
        Cursor cursor = readDatabase(READ_DB_PHONENUMBER,type);
        cursor.moveToFirst();
        //遍历游标
        do {
            //获取到游标中电话类型数据，并记录
            String phoneName = cursor.getString(cursor.getColumnIndexOrThrow(PhoneNumberEntry.COLUMNS_NAME_PHONENAME));
            //获取到游标中子表类型数据，并记录
            String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(PhoneNumberEntry.COLUMNS_NAME_PHONENUMBER));
            //手机类型实体对象实例化
            entity = new PhoneNumberEntity(phoneName,phoneNumber);
            //将实例化后的对象添加到mData中
            mData.add(entity);
        }while(cursor.moveToNext());

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
            adapter = new PhoneNumberAdapter(mData, PhoneNumberActivity.this);

            //给listview设置监听
            lv_phonenumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    index  = position;
                    new AlertDialog.Builder(PhoneNumberActivity.this)
                            .setTitle("警告！")
                            .setMessage("您将拨打："+ mData.get(position).getPhoneName()
                                    + "\n" + "电话：" + mData.get(position).getPhoneNumber()
                            )
                            .setPositiveButton("拨号", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //判断是否有拨打电话的权限，返回值为true是还没有授权或者已经拒绝
                                    if (ActivityCompat.checkSelfPermission(PhoneNumberActivity.this, Manifest.permission.CALL_PHONE)
                                            != PackageManager.PERMISSION_GRANTED) {
                                        //返回值为true，已经拒绝授权，返回值为false，还没选择是否授权
                                        if(ActivityCompat.shouldShowRequestPermissionRationale(PhoneNumberActivity.this,Manifest.permission.CALL_PHONE)  ){
                                        }else{
                                            //调用系统方法，设置权限
                                            ActivityCompat.requestPermissions(PhoneNumberActivity.this,
                                                    new String[]{Manifest.permission.CALL_PHONE},
                                                    TAG_PERMISSION);
                                            if (isOk){
                                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                callIntent.setData(Uri.parse("tel:" + mData.get(index).getPhoneNumber()));
                                                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(callIntent);
                                            }
                                        }
                                    }else{
//                                        Toast.makeText(PhoneNumberActivity.this, "已经授权", Toast.LENGTH_SHORT).show();
                                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                                        callIntent.setData(Uri.parse("tel:" + mData.get(index).getPhoneNumber()));
                                        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(callIntent);
                                    }
                                }
                            })
                            .setNegativeButton("取消",null)
                            .show();
                }
            });
            //给列表设置适配器
            lv_phonenumber.setAdapter(adapter);
            //加载单个的item动画
            Animation rightin = AnimationUtils.loadAnimation(PhoneNumberActivity.this,R.anim.rightin);
            //根据单个的item动画创建布局过渡动画控制器
            LayoutAnimationController lc = new LayoutAnimationController(rightin);
            //设置动画延迟时间
            lc.setDelay(0.2f);
            //设置动画播放次序
            lc.setOrder(LayoutAnimationController.ORDER_NORMAL);//随机播放
            //给目标ListView设置item布局过渡动画
            lv_phonenumber.setLayoutAnimation(lc);

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case TAG_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isOk = true;
                    Toast.makeText(PhoneNumberActivity.this, "已经授权", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PhoneNumberActivity.this, "拒绝授权", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
