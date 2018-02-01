package com.cyc.markdemo;

import android.app.Application;
import android.content.Context;

import com.cyc.markdemo.common.BaseApiService;

/**
 * Created by cyc20 on 2018/2/1.
 */

public class BaseApplication extends Application {

    private static BaseApplication INSTANCE=null;
    public static BaseApplication getINSTANCE(){
        return INSTANCE;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE=this;

    }
}
