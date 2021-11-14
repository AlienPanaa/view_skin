package com.alien.pannaa.skin;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class SkinInflaterFactory implements LayoutInflater.Factory2, Observer {

    private static final String[] SDK_VIEW_PREFIX = {
        "android.widget.",
        "android.webkit.",
        "android.app.",
        "android.view.",
        "androidx.appcompat.widget"
    };

    private static final Class<?>[] VIEW_CONSTRUCTOR_SIGNATURE = new Class[] {
            Context.class, AttributeSet.class};

    private static final Map<String, Constructor<? extends View>> CACHE_CONSTRUCTOR = new HashMap<>();

    private final ViewRecorder recorder = new ViewRecorder();

    public SkinInflaterFactory(Activity activity) {
        activity.getLayoutInflater().setFactory2(this);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {

        View view = createViewLikeSDK(name, context, attrs);

        if(view != null) {
            recorder.recorder(view, attrs);
        }

        return view;
    }

    @Nullable
    private View createViewLikeSDK(String name, Context context, AttributeSet
            attrs) {
        View view = null;

        // 3th party lib view
        if (-1 == name.indexOf('.')) {
            // test sdk default view.

            for(String pre : SDK_VIEW_PREFIX) {
                View tempView = createView(pre + name, context, attrs);

                if(tempView != null) {
                    view = tempView;
                    break;
                }
            }
        }

        // normal view
        if(view == null) {
            view = createView(name, context, attrs);
        }

        return view;

    }

    private View createView(String name, Context context, AttributeSet attrs) {
        View result = null;

        Constructor<? extends View> constructor = getConstructor(name, context);

        if(constructor != null) {
            try {
                result = constructor.newInstance(context, attrs);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                // ignore
            }
        }

        return result;
    }

    @Nullable
    private Constructor<? extends View> getConstructor(String name, Context context) {
        Constructor<? extends View> result = CACHE_CONSTRUCTOR.get(name);

        if(result == null) {
            try {
                Class<? extends View> aClass = context.getClassLoader().loadClass(name).asSubclass(View.class);
                result = aClass.getConstructor(VIEW_CONSTRUCTOR_SIGNATURE);
                CACHE_CONSTRUCTOR.put(name, result);
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                // ignore
            }
        }

        return result;
    }

    @Override
    public void update(Observable o, Object arg) {
        recorder.applySkin();
    }
}
