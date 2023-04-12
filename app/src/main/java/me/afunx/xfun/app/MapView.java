package me.afunx.xfun.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.afunx.xfun.common.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class MapView extends View {

    private static final String TAG = "MapView";

    private Bitmap mBitmap = Bitmap.createBitmap(1080, 2000, Bitmap.Config.ARGB_8888);

    private Bitmap mBitmapOut;

    //private final Canvas mCanvas = new Canvas();

    private Paint mPaint = new Paint();

    private Paint mPaintX;

    public MapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LogUtils.i(TAG, "MapView()");
        mPaint.setAntiAlias(true);
        test();
    }

    private void test() {
        // DST
        for (int i = 0; i < 1080; i++) {
            for (int j = 0; j < 1080; j++) {
                mBitmap.setPixel(i, j, Color.BLUE);
            }
        }
        mBitmapOut = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Rect rect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        Canvas canvas = new Canvas(mBitmapOut);
        // SRC
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(20f);
        canvas.drawLine(0, 0, 1600, 1600, mPaint);
        //canvas.drawRect(500, 500, 700, 1600, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mBitmap, rect, rect, mPaint);
        mPaint.setXfermode(null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmapOut, 0, 0, mPaint);
        int pixel = mBitmapOut.getPixel(0, 0);
        LogUtils.e(TAG, "pixel: " + Integer.toHexString(pixel));
        for (int i=0; i<30; i++) {
            pixel = mBitmapOut.getPixel(i, 0);
            LogUtils.e(TAG, i + "," + "0" + ":" + Integer.toHexString(pixel));
        }
        for (int i=0; i<30; i++) {
            pixel = mBitmapOut.getPixel(0, i);
            LogUtils.e(TAG, "0" + "," + i + ":" + Integer.toHexString(pixel));
        }

        List<Integer> pixels = new ArrayList<>();
        for (int i=0; i<mBitmapOut.getHeight(); i++) {
            for (int j=0; j<mBitmapOut.getWidth(); j++) {
                pixel = mBitmapOut.getPixel(j, i);
                if (!pixels.contains(pixel)) {
                    LogUtils.e(TAG, "pixel: " + Integer.toHexString(pixel));
                    pixels.add(pixel);
                }
            }
        }
    }
}
