package me.afunx.xfun.app.track;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.Objects;

import me.afunx.xfun.app.R;
import me.afunx.xfun.app.util.Ae2AndroidDimenUtil;

public class TrackRightEye extends TrackAbstractEye {

    private static final float RIGHT_EYE_CENTER_X = Ae2AndroidDimenUtil.ae2androidX(1213.0f);
    private static final float RIGHT_EYE_CENTER_Y = Ae2AndroidDimenUtil.ae2androidY(608.0f);
    private static final float RIGHT_EYE_BOTTOM_X = Ae2AndroidDimenUtil.ae2androidX(1213.5f);
    private static final float RIGHT_EYE_BOTTOM_Y = Ae2AndroidDimenUtil.ae2androidY(790.5f);

    @Override
    protected float eyeCenterX() {
        return RIGHT_EYE_CENTER_X;
    }

    @Override
    protected float eyeCenterY() {
        return RIGHT_EYE_CENTER_Y;
    }

    @Override
    protected float eyeBottomX() {
        return RIGHT_EYE_BOTTOM_X;
    }

    @Override
    protected float eyeBottomY() {
        return RIGHT_EYE_BOTTOM_Y;
    }

    @Override
    protected Bitmap eyeBitmapBottom(@NonNull Context context) {
        return ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_right_bottom))).getBitmap();
    }

    @Override
    protected Bitmap eyeBitmapOut(@NonNull Context context) {
        return ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_right_out))).getBitmap();
    }

    @Override
    protected Bitmap eyeBitmapIn(@NonNull Context context) {
        return ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_right_in))).getBitmap();
    }
}
