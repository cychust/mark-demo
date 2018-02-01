package com.cyc.markdemo.common;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.ErrorManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cyc20 on 2018/2/1.
 */

public class RetrofitClient {
    private static final int DEFALT_TIMEOUT = 20;
    private static final String TAG = "RetrofitClient";
    private BaseApiService mBaseApiService;
    private static OkHttpClient sOkHttpClient;
    private static String baseUrl = BaseApiService.Base_URL;
    private static Context sContext;
    private static RetrofitClient INSTANCE;
    //todo
    private static Retrofit mRetrofit;
    private Cache mCache = null;
    private File httpCachedDirectory = null;

    public static RetrofitClient getINSTANCE(Context context) {
        if (context != null) {
            sContext = context;
        }
        return new RetrofitClient(context);
    }

    public static RetrofitClient getINSTANCE(Context context, String url) {
        if (context != null) {
            sContext = context;
        }
        return new RetrofitClient(context, url);
    }

    public static RetrofitClient getINSTANCE(Context context, String url, Map<String, String> headers) {
        if (context != null) {
            sContext = context;
        }
        return new RetrofitClient(context, url, headers);
    }

    public RetrofitClient(Context context) {
        this(context, null);
    }

    public RetrofitClient(Context context, String url) {
        this(context, url, null);
    }

    public RetrofitClient(Context context, String url, Map<String, String> headers) {
        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }
        if (httpCachedDirectory == null) {
            httpCachedDirectory = new File(sContext.getCacheDir(), "cache");
        }
        try {
            if (mCache == null) {
                mCache = new Cache(httpCachedDirectory, 10 * 1024 * 1024);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        //创建okhttp
        sOkHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(mCache)
                .addInterceptor(new BaseInterceptor(headers))
                .connectTimeout(DEFALT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFALT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        //创建retrofit
        mRetrofit = new Retrofit.Builder()
                .client(sOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .build();
    }
}
