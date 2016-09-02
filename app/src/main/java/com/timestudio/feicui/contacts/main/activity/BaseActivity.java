package com.timestudio.feicui.contacts.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author ShenGuiqiang
 * @description Activity的基类
 */
public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏和状态栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
