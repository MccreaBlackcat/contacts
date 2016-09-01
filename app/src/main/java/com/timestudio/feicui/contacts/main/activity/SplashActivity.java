package com.timestudio.feicui.contacts.main.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.timestudio.feicui.contacts.R;

/**
 * @description 开屏动画
 */
public class SplashActivity extends BaseActivity {
    ImageView iv_splash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setContent() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        iv_splash = (ImageView)findViewById(R.id.iv_splash);
    }

    @Override
    protected void initListener() {

    }
    /**
     * @description 加载动画
     */
    private void initAnimation(){
        //淡入动画
        ValueAnimator alphaAnim = ObjectAnimator.ofFloat(iv_splash,"alpha",0.0f,1.0f);
        //设置动画时间
        alphaAnim.setDuration(3000);
        //设置动画结束后的监听
        alphaAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //播放结束后实现页面跳转
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                //结束释放动画界面
                finish();
            }
        });
        //开启动画
        alphaAnim.start();
    }
    /**
     * 当前页是否已获得焦点
     *
     * @param hasFocus true代表获得焦点，false相反
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            initAnimation();
        }
    }
}
