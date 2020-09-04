package com.ubtechinc.aimbothumming.network;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.ubtechinc.aimbothumming.utils.LogUtils;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String TAG = "ApiClient";
    // TODO 临时修改为外网调试地址
    private static final String BASE_URL = "https://prerelease.ubtrobot.com/";
    private static final long READ_TIMEOUT = 10;
    private static final long CONNECT_TIMEOUT = 10;
    private static final boolean DEBUG = true;

    private static String sPrevBaseUrl = BASE_URL;

    private Retrofit mRetrofit;

    public static ApiClient get() {
        return Singleton.instance;
    }

    private void initApiClient() {
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LogUtils.ee(TAG, "accept() throwable: " + throwable);
            }
        });
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        if (DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
//                    LogUtils.ii(TAG, message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        OkHttpClient okHttpClient = builder.build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(sPrevBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static String getCurrentUrl(Context context) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor =resolver.query(Uri.parse("content://com.ubt.aimbotlauncher.provider/property"), null, "server_beep", null, null);
        if (cursor != null) {
            if (cursor.moveToNext() && cursor.getColumnCount() > 2) {
                String url = cursor.getString(2);
                cursor.close();
                if (url != null && url.contains(".")) {
                    return url;
                }
            }
        }
        return null;
    }

    public synchronized  <T> T getService(Context context, Class<T> clazz) {

        String url = getCurrentUrl(context);

        boolean isUrlUpdate = (url != null && !url.equals(sPrevBaseUrl));

        if (isUrlUpdate) {
            sPrevBaseUrl = url;
        }

        LogUtils.ii(TAG, "getService() isUrlUpdate: " + isUrlUpdate + ", url: " + url + ", sPrevBaseUrl: " + sPrevBaseUrl);

        if (mRetrofit == null || isUrlUpdate) {
            initApiClient();
        }

        return mRetrofit.create(clazz);
    }

    private static class Singleton {
        private static ApiClient instance = new ApiClient();
    }
}
