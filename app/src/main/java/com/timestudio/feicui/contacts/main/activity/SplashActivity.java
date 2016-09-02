package com.timestudio.feicui.contacts.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
        //xml方式实现补间动画ViewAnimation
        //加载XML文件中的animation
        final Animation anim = AnimationUtils.loadAnimation(this,R.anim.anim_splash);
        //设置动画持续时间3000ms
        anim.setDuration(3000);
        //设置动画监听
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //设置Intent，实现动画结束后跳转到主界面
                Intent intent = new Intent();
                //设置从LogoActivity跳转到MainActivity
                intent.setClass(SplashActivity.this,PhoneTypeActivity.class);
                //跳转界面
                startActivity(intent);
                //跳转结束后结束动画界面
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //开启动画：方式1
        iv_splash.setAnimation(anim);
        anim.start();
        //开启动画:方式2
//        im_logo.startAnimation(anim);
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
