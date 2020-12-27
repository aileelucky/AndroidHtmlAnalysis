package com.gu.androidhtmlanalysis;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by 顾佳佳 on 2020/12/27.
 */
public class AnalysisApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
