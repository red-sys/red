package com.haha.baidumap;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Zs on 2019/2/23.
 */

abstract class BaseActivity extends AppCompatActivity {
    private MainLayout mContentView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null && mContentView != null) {
            mContentView.setCanRequestLayout(false);
            setContentView(mContentView);
            return;
        }
        super.onCreate(savedInstanceState);
        int layoutResID = getLayoutResID();
        View v = getContentView();
        if (layoutResID != 0 || v != null) {
            if (layoutResID == 0) {
                mContentView = new MainLayout(this);
                mContentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                mContentView.addView(v);
                setContentView(mContentView);
            } else {
                mContentView = new MainLayout(this);
                mContentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                View.inflate(this, layoutResID, mContentView);
                setContentView(mContentView);
            }
            Window window = getWindow();
            if (window != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.setStatusBarColor(Color.TRANSPARENT);
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                }
            }
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initView();
        initData();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initView();
        initData();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        initView();
        initData();
    }

    protected void initView() {

    }

    protected void initData() {
    }

    protected View getContentView() {
        return null;
    }
    protected abstract @LayoutRes
    int getLayoutResID();

}
