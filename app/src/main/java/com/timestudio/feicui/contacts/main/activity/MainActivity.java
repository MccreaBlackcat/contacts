package com.timestudio.feicui.contacts.main.activity;


import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.Toast;

import com.timestudio.feicui.contacts.R;
import com.timestudio.feicui.contacts.main.adapter.PhoneTypeAdapter;
import com.timestudio.feicui.contacts.main.entity.PhoneTypeEntity;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    //电话类型列表
    ListView lv_phoneType;
    //是否进入双击退出计时状态
    boolean isExit = false;
    //第一次点击退出的时间戳
    long l_firstClickTime;
    //第二次点击退出的时间戳
    long l_secondClickTime;
    //声明数据集合
    ArrayList<PhoneTypeEntity> data;
    @Override
    protected int setContent() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        lv_phoneType = (ListView)findViewById(R.id.lv_phoneType);
        initList();
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
     * 初始电话类型列表
     */
    protected void initList(){
        data = new ArrayList<>();
        //添加测试数据
        for (int i = 0; i < 20 ; i ++){
            PhoneTypeEntity entity = new PhoneTypeEntity();
            entity.setPhoneTypeName("电话类型" + i);
            data.add(entity);
        }
        //初始化适配器
        PhoneTypeAdapter adapter = new PhoneTypeAdapter(this, data);
        //给列表设置适配器
        lv_phoneType.setAdapter(adapter);
    }
    /**
     * 双击退出函数
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
            if(( l_secondClickTime - l_secondClickTime ) < 2000){
                finish();
            }else {
                isExit = false; //重置记录退出标志
                doubleClickExit(keyCode,event); //超出2000ms时，重新开始逻辑函数
            }
        }
    }
}
