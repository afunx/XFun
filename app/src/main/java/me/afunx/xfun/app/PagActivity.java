package me.afunx.xfun.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afunx.xfun.common.utils.LogUtils;

import org.libpag.PAGComposition;
import org.libpag.PAGDiskCache;
import org.libpag.PAGFile;
import org.libpag.PAGImageView;
import org.libpag.PAGPlayer;
import org.libpag.PAGTimeStretchMode;
import org.libpag.PAGView;

import me.afunx.xfun.app.util.TimeDiffUtil;

public class PagActivity extends AppCompatActivity {

    private static final String TAG = "PagActivity";

    private PAGImageView mPAGView;

    private TextView mTvFrameRate;

    private Handler mMainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag);
        mPAGView = findViewById(R.id.pag_view);
        mTvFrameRate = findViewById(R.id.tv_frame_rate);
        initPagView2(mPAGView);
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
    }

    private void updateFrameRate(int rate) {
        mMainHandler.removeCallbacksAndMessages(null);
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                mTvFrameRate.setText(String.valueOf(rate));
            }
        });
    }

}