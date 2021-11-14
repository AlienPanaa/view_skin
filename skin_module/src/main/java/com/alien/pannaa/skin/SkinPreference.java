package com.alien.pannaa.skin;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

public class SkinPreference {
    private static final String SKIN_SHARED = "skins";
    private static final String KEY_SKIN_PATH = "skin-path";

    private volatile static SkinPreference instance;

    private final SharedPreferences sharedPreferences;

    private SkinPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(SKIN_SHARED, Context.MODE_PRIVATE);
    }

    static void init(Context context) {
        if(instance == null) {
            synchronized(SkinPreference.class) {
                if(instance == null) {
                    instance = new SkinPreference(context);
                }
            }
        }
    }

    public static SkinPreference getInstance() {
        return instance;
    }

    public void setSkinPath(String skinPath) {
        sharedPreferences.edit().putString(KEY_SKIN_PATH, skinPath).apply();
    }

    @Nullable
    public String getSkinPath() {
        return sharedPreferences.getString(KEY_SKIN_PATH, null);
    }

    public void reset() {
        sharedPreferences.edit().remove(KEY_SKIN_PATH).apply();
    }

}
