package com.alien.pannaa.skin;

import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SkinManager.init(this);
    }
}
