package com.alien.pannaa.skin.resource;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public abstract class ResourceState {

    private final Resources resources;

    public ResourceState(Resources resources) {
        this.resources = resources;
    }

    public abstract int getIdentifier(int resId);

    public abstract int getColor(int resId);

    public abstract ColorStateList getColorStateList(int resId);

    public abstract Drawable getDrawable(int resId);

    public Object getBackground(int resId) {
        String resourceTypeName = resources.getResourceTypeName(resId);

        if("color".equalsIgnoreCase(resourceTypeName)) {
            return getColor(resId);
        } else {
            return getDrawable(resId);
        }
    }
}
