package com.alien.pannaa.skin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

public class ChangeSkinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_skin);
    }

    public void reset(View view) {
        SkinManager.getInstance().loadSkin("");
    }

    @SuppressLint("SdCardPath")
    public void load(View view) {
        SkinManager.getInstance().loadSkin("/data/data/com.alien.pannaa.skin/skin/test_skin-debug.apk");
    }

}