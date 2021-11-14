package com.alien.pannaa.skin;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alien.pannaa.skin.application.ApplicationLifecycle;
import com.alien.pannaa.skin.resource.ResourceStateManager;

import java.lang.reflect.Method;
import java.util.Observable;

/**
 * 給使用者直接使用
 */
public class SkinManager extends Observable {
    private volatile static SkinManager instance;

    private final Application application;

    private SkinManager(Application application) {
        this.application = application;

        SkinPreference.init(application);
        ResourceStateManager.init(application);

        application.registerActivityLifecycleCallbacks(new ApplicationLifecycle(this));

        loadSkin(SkinPreference.getInstance().getSkinPath());
    }

    public static void init(Application application) {
        if(instance == null) {
            synchronized (SkinManager.class) {
                if(instance == null) {
                    instance = new SkinManager(application);
                }
            }
        }
    }

    public static SkinManager getInstance() {
        return instance;
    }

    public void loadSkin(String skinPath) {
        if(handleLoadSkin(skinPath)) {
            SkinPreference.getInstance().setSkinPath(skinPath);
        } else {
            SkinPreference.getInstance().reset();
        }

        startNotifyObservers();
    }

    private boolean handleLoadSkin(String skinPath) {
        if(TextUtils.isEmpty(skinPath)) {
            ResourceStateManager.getInstance().setDefaultResource(application);
            return false;
        }

        try {
            String packageName = getPackageName(skinPath);

            if(TextUtils.isEmpty(packageName)) {
                throw new Exception("Cannot get skin package name");
            }

            Resources appResources = application.getResources();

            AssetManager assetManager = getAssetManagerAddAssetPath(skinPath);

            Resources skinResource = new Resources(assetManager,
                    appResources.getDisplayMetrics(), appResources.getConfiguration());

            ResourceStateManager.getInstance().setOtherSkinResource(application, skinResource, packageName);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    private AssetManager getAssetManagerAddAssetPath(@NonNull String skinPath) throws Exception, IllegalAccessException {
        AssetManager assetManager = AssetManager.class.newInstance();

        Method addAssetPath = assetManager.getClass().getMethod("addAssetPath",
                String.class);

        addAssetPath.invoke(assetManager, skinPath);

        return assetManager;
    }

    @Nullable
    private String getPackageName(@NonNull String skinPath) {
        PackageManager packageManager = application.getPackageManager();

        PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES);

        return packageArchiveInfo.packageName;
    }

    private void startNotifyObservers() {
        setChanged();
        notifyObservers(null);
    }

}
