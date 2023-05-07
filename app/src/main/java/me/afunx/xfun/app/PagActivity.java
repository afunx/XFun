package me.afunx.xfun.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.TextView;

import com.afunx.xfun.common.utils.LogUtils;

import org.libpag.PAGComposition;
import org.libpag.PAGFile;
import org.libpag.PAGImageView;
import org.libpag.PAGView;

public class PagActivity extends AppCompatActivity {

    private static final String TAG = "PagActivity";

    private PAGImageView mPAGImageView;

    private PAGView mPAGView;

    private TextView mTvFrameRate;

    private final Handler mMainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag);
        mPAGImageView = findViewById(R.id.pag_image_view);
        mPAGView = findViewById(R.id.pag_view);
        mTvFrameRate = findViewById(R.id.tv_frame_rate);
        initPagView2(mPAGImageView);
        //initPagView3(mPAGView);
    }

    private void initPagView(PAGImageView pagImageView) {
        final int maxCount = 3;
        int count = 0;
        PAGFile pagFile = PAGFile.Load(getAssets(), "charging_b.pag");


        ++count;
        PAGComposition pagComposition = PAGComposition.Make(512, 320);
        if (count <= maxCount) {
            pagComposition.addLayer(pagFile);
        }

        ++count;
        Matrix matrix = new Matrix();
        if (count <= maxCount) {
            PAGFile pagFile1 = pagFile.copyOriginal();
            matrix.postTranslate(0, -100);
            pagFile1.setMatrix(matrix);
            pagComposition.addLayer(pagFile1);
        }

        ++count;
        if (count <= maxCount) {
            PAGFile pagFile2 = pagFile.copyOriginal();
            matrix.reset();
            matrix.postTranslate(0, -50);
            pagFile2.setMatrix(matrix);
            pagComposition.addLayer(pagFile2);
        }

        ++count;
        if (count <= maxCount) {
            PAGFile pagFile3 = pagFile.copyOriginal();
            matrix.reset();
            matrix.postTranslate(0, 50);
            pagFile3.setMatrix(matrix);
            pagComposition.addLayer(pagFile3);
        }

        ++count;
        if (count <= maxCount) {
            PAGFile pagFile4 = pagFile.copyOriginal();
            matrix.reset();
            matrix.postTranslate(0, 100);
            pagFile4.setMatrix(matrix);
            pagComposition.addLayer(pagFile4);
        }

        pagImageView.setComposition(pagComposition);
        pagImageView.setRepeatCount(0);
        pagImageView.addListener(new PAGImageView.PAGImageViewListener() {

            private final long interval = 1000;

            private long lastTimestamp = 0;

            private int frameCount = 0;

            @Override
            public void onAnimationStart(PAGImageView pagImageView) {

            }

            @Override
            public void onAnimationEnd(PAGImageView pagImageView) {

            }

            @Override
            public void onAnimationCancel(PAGImageView pagImageView) {

            }

            @Override
            public void onAnimationRepeat(PAGImageView pagImageView) {

            }

            @Override
            public void onAnimationUpdate(PAGImageView pagImageView) {
                long current = SystemClock.elapsedRealtime();
                if (lastTimestamp == 0) {
                    lastTimestamp = current;
                } else {
                    if (current - lastTimestamp >= interval) {
                        ++frameCount;
                        updateFrameRate(frameCount);
                        frameCount = 0;
                        lastTimestamp = current;
                    } else {
                        ++frameCount;
                    }
                }
            }
        });

        pagImageView.play();
    }

    private void initPagView2(PAGImageView pagImageView) {

        PAGComposition pagComposition = PAGComposition.Make(512, 320);

        PAGFile pagFileA = PAGFile.Load(getAssets(), "charging_a.pag");
        pagComposition.addLayer(pagFileA);

        PAGFile pagFileB = PAGFile.Load(getAssets(), "charging_b.pag");
        pagComposition.addLayer(pagFileB);

        PAGFile pagFileC = PAGFile.Load(getAssets(), "charging_c.pag");
        pagComposition.addLayer(pagFileC);

        pagImageView.setComposition(pagComposition);
        pagImageView.setRepeatCount(0);
        pagImageView.addListener(new PAGImageView.PAGImageViewListener() {

            private final long interval = 1000;

            private long lastTimestamp = 0;

            private int frameCount = 0;

            @Override
            public void onAnimationStart(PAGImageView pagImageView) {

            }

            @Override
            public void onAnimationEnd(PAGImageView pagImageView) {

            }

            @Override
            public void onAnimationCancel(PAGImageView pagImageView) {

            }

            @Override
            public void onAnimationRepeat(PAGImageView pagImageView) {

            }

            @Override
            public void onAnimationUpdate(PAGImageView pagImageView) {
                long current = SystemClock.elapsedRealtime();
                if (lastTimestamp == 0) {
                    lastTimestamp = current;
                } else {
                    if (current - lastTimestamp >= interval) {
                        ++frameCount;
                        updateFrameRate(frameCount);
                        frameCount = 0;
                        lastTimestamp = current;
                    } else {
                        ++frameCount;
                    }
                }
            }
        });

        pagImageView.play();


        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int memClass = activityManager.getMemoryClass();//以M为单位，系统为应用分配的内存
        int largeMemoryClass = activityManager.getLargeMemoryClass();//以M为单位，系统可提供给应用的最大内存使用值
        LogUtils.e(TAG, "afunx memClass: " + memClass + ", largeMemoryClass: " + largeMemoryClass);
        Float totalMemory = Runtime.getRuntime().totalMemory()*1.0f/(1024*1024);//以字节为单位转为M，该应用已占用内存（包含实际应用内存和空闲内存freeMemory）
        Float freeMemory = Runtime.getRuntime().freeMemory()*1.0f/(1024*1024);//以字节为单位转为M，该应用已占用的空闲内存
        Float maxMemory = Runtime.getRuntime().maxMemory()*1.0f/(1024*1024);//以字节为单位转为M，该应用最大可使用内存
        LogUtils.e(TAG, "afunx totalMemory: " + totalMemory + ", freeMemory: " + freeMemory + ", maxMemory: " + maxMemory);
    }

    private void initPagView3(PAGView pagView) {

        PAGComposition pagComposition = PAGComposition.Make(512, 320);

        PAGFile pagFileA = PAGFile.Load(getAssets(), "charging_a.pag");
        pagComposition.addLayer(pagFileA);

        PAGFile pagFileB = PAGFile.Load(getAssets(), "charging_b.pag");
        pagComposition.addLayer(pagFileB);

        PAGFile pagFileC = PAGFile.Load(getAssets(), "charging_c.pag");
        pagComposition.addLayer(pagFileC);

        pagView.setComposition(pagComposition);
        pagView.setRepeatCount(0);
        pagView.addListener(new PAGView.PAGViewListener() {
            @Override
            public void onAnimationStart(PAGView pagView) {

            }

            @Override
            public void onAnimationEnd(PAGView pagView) {

            }

            @Override
            public void onAnimationCancel(PAGView pagView) {

            }

            @Override
            public void onAnimationRepeat(PAGView pagView) {

            }

            @Override
            public void onAnimationUpdate(PAGView pagView) {

            }
        });
        pagView.addListener(new PAGView.PAGViewListener() {

            private final long interval = 1000;

            private long lastTimestamp = 0;

            private int frameCount = 0;

            @Override
            public void onAnimationStart(PAGView pagView) {

            }

            @Override
            public void onAnimationEnd(PAGView pagView) {

            }

            @Override
            public void onAnimationCancel(PAGView pagView) {

            }

            @Override
            public void onAnimationRepeat(PAGView pagView) {

            }

            @Override
            public void onAnimationUpdate(PAGView pagView) {
                long current = SystemClock.elapsedRealtime();
                if (lastTimestamp == 0) {
                    lastTimestamp = current;
                } else {
                    if (current - lastTimestamp >= interval) {
                        ++frameCount;
                        updateFrameRate(frameCount);
                        frameCount = 0;
                        lastTimestamp = current;
                    } else {
                        ++frameCount;
                    }
                }
            }
        });

        pagView.setCacheEnabled(true);

        pagView.play();


        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int memClass = activityManager.getMemoryClass();//以M为单位，系统为应用分配的内存
        int largeMemoryClass = activityManager.getLargeMemoryClass();//以M为单位，系统可提供给应用的最大内存使用值
        LogUtils.e(TAG, "afunx memClass: " + memClass + ", largeMemoryClass: " + largeMemoryClass);
        Float totalMemory = Runtime.getRuntime().totalMemory()*1.0f/(1024*1024);//以字节为单位转为M，该应用已占用内存（包含实际应用内存和空闲内存freeMemory）
        Float freeMemory = Runtime.getRuntime().freeMemory()*1.0f/(1024*1024);//以字节为单位转为M，该应用已占用的空闲内存
        Float maxMemory = Runtime.getRuntime().maxMemory()*1.0f/(1024*1024);//以字节为单位转为M，该应用最大可使用内存
        LogUtils.e(TAG, "afunx totalMemory: " + totalMemory + ", freeMemory: " + freeMemory + ", maxMemory: " + maxMemory);
    }

    private void updateFrameRate(int rate) {
        mMainHandler.removeCallbacksAndMessages(null);
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                mTvFrameRate.setText(String.valueOf(rate));
                Float totalMemory = Runtime.getRuntime().totalMemory()*1.0f/(1024*1024);//以字节为单位转为M，该应用已占用内存（包含实际应用内存和空闲内存freeMemory）
                Float freeMemory = Runtime.getRuntime().freeMemory()*1.0f/(1024*1024);//以字节为单位转为M，该应用已占用的空闲内存
                Float maxMemory = Runtime.getRuntime().maxMemory()*1.0f/(1024*1024);//以字节为单位转为M，该应用最大可使用内存
                LogUtils.e(TAG, "afunx totalMemory: " + totalMemory + ", freeMemory: " + freeMemory + ", maxMemory: " + maxMemory);
            }
        });
    }

}