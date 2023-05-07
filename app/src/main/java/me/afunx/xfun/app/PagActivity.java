package me.afunx.xfun.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.afunx.xfun.common.utils.LogUtils;

import org.libpag.PAGFile;
import org.libpag.PAGImageView;
import org.libpag.PAGView;

import me.afunx.xfun.app.util.TimeDiffUtil;

public class PagActivity extends AppCompatActivity {

    private static final String TAG = "PagActivity";

    private PAGImageView mPAGView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag);
        mPAGView = findViewById(R.id.pag_view);
        initPagView();
    }

    private void initPagView() {
        TimeDiffUtil.start();
        PAGFile pagFile = PAGFile.Load(getAssets(), "charging_b.pag");
        int layerType = pagFile.layerType();
        int numImages = pagFile.numImages();
        int numVideos = pagFile.numVideos();
        int numChildren = pagFile.numChildren();
        LogUtils.e(TAG, "afunx layerType: " + layerType);
        LogUtils.e(TAG, "afunx numImages: " + numImages);
        LogUtils.e(TAG, "afunx numVideos: " + numVideos);
        LogUtils.e(TAG, "afunx numChildren: " + numChildren);
        long consume = TimeDiffUtil.end();
        LogUtils.i(TAG, "initPagView() load consume: " + consume + " ms");
        TimeDiffUtil.start();
        mPAGView.setComposition(pagFile);
        consume = TimeDiffUtil.end();
        LogUtils.i(TAG, "initPagView() setComposition consume: " + consume + " ms");
        mPAGView.setRepeatCount(0);
        mPAGView.play();
    }


}