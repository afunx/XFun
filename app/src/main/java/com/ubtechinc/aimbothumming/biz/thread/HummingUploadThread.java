package com.ubtechinc.aimbothumming.biz.thread;

import android.content.Context;

import com.ubtechinc.aimbothumming.biz.HummingFrame;
import com.ubtechinc.aimbothumming.biz.HummingFrameRouter;
import com.ubtechinc.aimbothumming.biz.impl.HummingFrameRouterImpl;
import com.ubtechinc.aimbothumming.network.UploadHummingRespository;
import com.ubtechinc.aimbothumming.utils.LogUtils;
//import com.ubtrobot.Robot;
//import com.ubtrobot.async.Promise;
//import com.ubtrobot.property.PropertyException;
//import com.ubtrobot.property.PropertyManager;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.ubtechinc.aimbothumming.biz.HummingFrameRouter.UPLOAD_HUMMING_FRAME_COUNT;

public class HummingUploadThread extends AbstractTaskThread {

    private static final String TAG = "HummingUploadThread";

    private static String sSerialNumber = null;

    private HummingFrameRouter mHummingFrameRouter = HummingFrameRouterImpl.get();
    private Call<ResponseBody> mBodyCall;

    private int detectType;
    private long timestamp;
    private int locationFlag;
    private double x;
    private double y;
    private byte[] buffer = null;
    private Context mContext;

    private String sn;

    public HummingUploadThread(Context context) {
        super("HummingUploadThread");
        mContext = context;
        Runnable taskRunnable = new Runnable() {

            @Override
            public void run() {
                // TODO StorageCache优先处理
                HummingFrame[] frames = mHummingFrameRouter.getUploadHummingFrames();
                if (frames == null) {
                    return;
                }
                allocBuffer(frames);
                updateBuffer(frames);
                updateOthers(frames);
                if (detectType == 0) {
                    LogUtils.ii(TAG, "run() detectType=0");
                    return;
                }
                boolean suc = postFramesToServer();
                if (suc) {
                    mHummingFrameRouter.doUploadSuc();
                } else {
                    mHummingFrameRouter.doUploadFail();
                }
            }
        };
        setRunnable(taskRunnable);
    }

    private void updateBuffer(HummingFrame[] hummingFrames) {
        LogUtils.dd(TAG, "updateBuffer()");
        for (int i = 0, offset = 0; i < hummingFrames.length; i++) {
            byte[] src = hummingFrames[i].getBytes();
            System.arraycopy(src, 0, buffer, offset, src.length);
            offset += src.length;
        }
    }

    private void updateOthers(HummingFrame[] hummingFrames) {
        int middle = hummingFrames.length / 2;
        x = hummingFrames[middle].getLocationX();
        y = hummingFrames[middle].getLocationY();
        locationFlag = hummingFrames[middle].getLocationFlag();
        timestamp = hummingFrames[middle].getTimestamp();
        detectType = hummingFrames[middle].getDetectType();
        LogUtils.dd(TAG, "updateOthers() timestamp: " + timestamp + ", detectType: " + detectType + ", x: " + x + ", y: " + y);
    }

    private boolean postFramesToServer() {
        Call<ResponseBody> bodyCall = UploadHummingRespository.uploadHumming(mContext, buffer, sn, timestamp, locationFlag, x, y, detectType);
        mBodyCall = bodyCall;

        Response<ResponseBody> response = null;
        try {
            response = bodyCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtils.ii(TAG, "postFramesToServer() response: " + response);
        return response != null && response.code() == 200;
    }

    private void allocBuffer(HummingFrame[] frames) {
        if (buffer == null) {
            LogUtils.dd(TAG, "allocBuffer()");
            int size = frames[0].getBytes().length * UPLOAD_HUMMING_FRAME_COUNT;
            buffer = new byte[size];
        }
    }

    private String getSerialNumber() {
//        if (!TextUtils.isEmpty(sSerialNumber)) {
//            LogUtils.ii(TAG, "getSerialNumber() gain already, serialNumber: " + sSerialNumber);
//            return sSerialNumber;
//        }
//        Promise<String, PropertyException> promise = new PropertyManager(Robot.globalContext())
//                .getProperty("robot.sn.get", "");
//        // 若是获取机器人序列号失败，则再休眠10s并重试
//        while (TextUtils.isEmpty(sSerialNumber) && !mHummingCache.isStopped()) {
//            try {
//                sSerialNumber = promise.get();
//                if (TextUtils.isEmpty(sSerialNumber) && !mHummingCache.isStopped()) {
//                    Thread.sleep(10 * 1000);
//                }
//            } catch (Exception e) {
//                LogUtils.ee(TAG, "getSerialNumber() e: " + e);
//            }
//        }
//        LogUtils.ii(TAG, "getSerialNumber() serialNumber: " + sSerialNumber);
//        return sSerialNumber;
        return "Aimobt.01.1234567890ab";
    }

    @Override
    protected void onStart() {
        LogUtils.ii(TAG, "onStart()");
        sn = getSerialNumber();
    }

    @Override
    protected void onStop() {
        LogUtils.ii(TAG, "onStop()");
    }

    @Override
    protected void stop0() {
        if (mBodyCall != null) {
            LogUtils.ii(TAG, "stop0() cancel");
            mBodyCall.cancel();
        }
    }
}
