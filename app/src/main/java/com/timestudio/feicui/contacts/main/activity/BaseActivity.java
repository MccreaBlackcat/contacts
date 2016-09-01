package com.timestudio.feicui.contacts.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * @author ShenGuiqiang
 * @description Activity的基类
 */
public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //初始化Activity
        setContentView(setContent());
        initView();
        initListener();
    }
    /**
     * @description 装载页面
     */
    protected abstract int setContent();
    /**
     * @description 装载控件
     */
    protected abstract void initView();
    /**
     * @description 设置监听
     */
    protected abstract void initListener();
}
