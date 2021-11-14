package com.alien.pannaa.skin.resource;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class ResourceStateManager {

    private ResourceState resourceState;

    public ResourceStateManager(Context context) {
        resourceState = new DefaultResource(context);
    }

    public void setDefaultResource(Context context) {
        resourceState = new DefaultResource(context);
    }

    public void setOtherSkinResource(Context context, Resources skinResource, String path) {
        resourceState = new OtherResource(context, skinResource, path);
    }

    public int getIdentifier(int resId) {
        return resourceState.getIdentifier(resId);
    }

    public int getColor(int resId) {
        return  resourceState.getColor(resId);
    }

    public ColorStateList getColorStateList(int resId) {
        return  resourceState.getColorStateList(resId);
    }

    public Drawable getDrawable(int resId) {
        return  resourceState.getDrawable(resId);
    }

    public Object getBackground(int resId) {
        return  resourceState.getBackground(resId);
    }

}
