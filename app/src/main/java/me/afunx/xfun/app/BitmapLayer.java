package me.afunx.xfun.app;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import androidx.annotation.NonNull;

public class BitmapLayer {

    private final int mIdSize;
    private final int mFirstId;

    private final int[] mIds;

    private final Matrix mMatrix = new Matrix();

    public BitmapLayer(int idSize, int firstId) {
        this.mIdSize = idSize;
        this.mFirstId = firstId;
        this.mIds = new int[idSize];
        for (int i = 0; i < idSize; i++) {
            this.mIds[i] = firstId + i;
        }
    }

    private int mIndex;

    private Bitmap mBitmap;

    private BitmapFactory.Options mOptions;

    public Bitmap update(@NonNull Resources resources) {
        if (mOptions == null) {
            mOptions = new BitmapFactory.Options();
            mOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(resources, mIds[mIndex], mOptions);
            mOptions.inJustDecodeBounds = false;
        }
        return mBitmap;
    }
}
