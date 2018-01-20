package com.kx.admin.skinapk;

import android.app.Application;

import com.xk.skinlibrary.SkinManager;

/**
 * Created by admin on 2018/1/2.
 */

public class SkinApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.init(getApplicationContext());
    }
}
