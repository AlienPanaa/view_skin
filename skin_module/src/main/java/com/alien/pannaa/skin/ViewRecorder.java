package com.alien.pannaa.skin;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.alien.pannaa.skin.resource.ResourceStateManager;

import java.util.ArrayList;
import java.util.List;

public class ViewRecorder {
    private static final String TAG = ViewRecorder.class.getSimpleName();

    private static final List<String> ATTRIBUTES = new ArrayList<String>() {
        {
            add("background");
            add("src");
            add("textColor");
        }
    };

    private final List<XmlViewAttributes> attributes = new ArrayList<>();

    void recorder(View view, AttributeSet attrs) {
        List<ViewInfoItem> viewInfoItems = new ArrayList<>();

        for(int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);

            if(!ATTRIBUTES.contains(attrName.trim()) || attrValue.startsWith("#")) {
                continue;
            }

            int resId;
            if(attrValue.startsWith("?")) {     // System attr
                int attrId = Integer.parseInt(attrValue.substring(1));
                resId = 0;  // TODO:
            } else {
                resId = Integer.parseInt(attrValue.substring(1));
            }
            Log.i(TAG, "view: " + view.getClass().getName() + ", attrName: " + attrName + ", value: " + attrValue);

            viewInfoItems.add(new ViewInfoItem(attrName, resId));
        }

        if(!viewInfoItems.isEmpty()) {
            XmlViewAttributes XmlViewAttributes = new XmlViewAttributes(view, viewInfoItems);

            XmlViewAttributes.applySkin();
            attributes.add(XmlViewAttributes);
        }
    }

    void applySkin() {
        for (XmlViewAttributes mSkinView : attributes) {
            mSkinView.applySkin();
        }
    }

    static class XmlViewAttributes {
        View view;
        List<ViewInfoItem> itemViewInfoList;

        public XmlViewAttributes(View view, List<ViewInfoItem> itemViewInfoList) {
            this.view = view;
            this.itemViewInfoList = itemViewInfoList;
        }

        private void applySkin() {
            for(ViewInfoItem attr : itemViewInfoList) {
                switch (attr.name) {
                    case "background":
                        Object background = ResourceStateManager.getInstance().getBackground(attr
                                .resId);
                        //背景可能是 @color 也可能是 @drawable
                        if (background instanceof Integer) {
                            view.setBackgroundColor((int) background);
                        } else {
                            ViewCompat.setBackground(view, (Drawable) background);
                        }
                        break;

                    case "src":
                        background = ResourceStateManager.getInstance().getBackground(attr
                                .resId);
                        if (background instanceof Integer) {
                            ((ImageView) view).setImageDrawable(new ColorDrawable((Integer)
                                    background));
                        } else {
                            ((ImageView) view).setImageDrawable((Drawable) background);
                        }
                        break;

                    case "textColor":
                        ((TextView) view).setTextColor(ResourceStateManager.getInstance().getColorStateList
                                (attr.resId));
                        break;
                }
            }
        }
    }

    static class ViewInfoItem {
        String name;
        int resId;

        public ViewInfoItem(String name, int resId) {
            this.name = name;
            this.resId = resId;
        }
    }

}
