package com.alien.pannaa.skin;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

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
                Log.i(TAG, "attrName: " + attrName + ", value: " + attrValue);
                continue;
            }

            int resId;
            if(attrValue.startsWith("?")) {     // System attr
                int attrId = Integer.parseInt(attrValue.substring(1));
                resId = 0;  // TODO:
            } else {
                resId = Integer.parseInt(attrValue.substring(1));
            }

            viewInfoItems.add(new ViewInfoItem(attrName, resId));
        }

        if(!viewInfoItems.isEmpty()) {
            XmlViewAttributes XmlViewAttributes = new XmlViewAttributes(view, viewInfoItems);

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
        List<ViewInfoItem> itemViewInfos;

        public XmlViewAttributes(View view, List<ViewInfoItem> itemViewInfos) {
            this.view = view;
            this.itemViewInfos = itemViewInfos;
        }

        public void applySkin() {
            for(ViewInfoItem attr : itemViewInfos) {
                switch (attr.name) {
                    case "background":
                        break;

                    case "src":
                        break;

                    case "textColor":
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
