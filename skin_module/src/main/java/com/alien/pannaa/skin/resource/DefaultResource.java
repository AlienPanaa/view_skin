package com.alien.pannaa.skin.resource;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

public class DefaultResource extends ResourceState {

    public DefaultResource(Context context) {
        super(context.getResources());
    }

    @Override
    public int getIdentifier(int resId) {
        return resId;
    }

    @Override
    public int getColor(int resId) {
        return resources.getColor(resId);
    }

    @Override
    public ColorStateList getColorStateList(int resId) {
        return resources.getColorStateList(resId);
    }

    @Override
    public Drawable getDrawable(int resId) {
        return resources.getDrawable(resId);
    }

}
