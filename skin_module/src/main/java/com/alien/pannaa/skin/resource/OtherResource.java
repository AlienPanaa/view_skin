package com.alien.pannaa.skin.resource;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class OtherResource extends ResourceState {

    private final String skinPackageName;

    private final Resources defaultResource;
    private final Resources skinResource;

    public OtherResource(Context context, Resources skinResource, String skinPackageName) {
        super(context.getResources());

        defaultResource = context.getResources();

        this.skinResource = skinResource;
        this.skinPackageName = skinPackageName;
    }

    @Override
    public int getIdentifier(int resId) {

        String resName = defaultResource.getResourceName(resId);
        String resourceEntryName = defaultResource.getResourceEntryName(resId);
        String resType = defaultResource.getResourceTypeName(resId);

        return skinResource.getIdentifier(resourceEntryName, resType, skinPackageName);
    }

    @Override
    public int getColor(int resId) {

        int skinId = getIdentifier(resId);

        if(skinId == 0) {
            return defaultResource.getColor(resId);
        }

        return skinResource.getColor(skinId);
    }

    @Override
    public ColorStateList getColorStateList(int resId) {

        int skinId = getIdentifier(resId);

        if(skinId == 0) {
            return defaultResource.getColorStateList(resId);
        }

        return skinResource.getColorStateList(skinId);
    }

    @Override
    public Drawable getDrawable(int resId) {

        int skinId = getIdentifier(resId);

        if(skinId == 0) {
            return defaultResource.getDrawable(resId);
        }

        return skinResource.getDrawable(skinId);
    }

}