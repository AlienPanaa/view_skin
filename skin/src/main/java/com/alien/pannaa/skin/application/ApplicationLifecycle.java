package com.alien.pannaa.skin.application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alien.pannaa.skin.SkinInflaterFactory;
import com.alien.pannaa.skin.SkinManager;

import java.lang.reflect.Field;
import java.util.Observable;

public class ApplicationLifecycle implements Application.ActivityLifecycleCallbacks {

    private final Observable observable;
    private final ArrayMap<Activity, SkinInflaterFactory> layoutInflaterFactories = new
            ArrayMap<>();

    public ApplicationLifecycle(Observable observable) {
        this.observable = observable;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();

        try {
            @SuppressLint("SoonBlockedPrivateApi")
            Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
            field.setAccessible(true);
            field.setBoolean(layoutInflater, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 設定加載工廠
        SkinInflaterFactory factory = new SkinInflaterFactory(activity);
        layoutInflater.setFactory(factory);


        layoutInflaterFactories.put(activity, factory);
        observable.addObserver(factory);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        SkinInflaterFactory observer = layoutInflaterFactories.remove(activity);
        SkinManager.getInstance().deleteObserver(observer);
    }
}
