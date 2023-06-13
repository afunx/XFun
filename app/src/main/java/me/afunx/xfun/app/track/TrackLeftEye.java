package me.afunx.xfun.app.track;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.Objects;

import me.afunx.xfun.app.R;
import me.afunx.xfun.app.util.Ae2AndroidDimenUtil;

public class TrackLeftEye extends TrackAbstractEye {
    private static final float LEFT_EYE_CENTER_X = Ae2AndroidDimenUtil.ae2androidX(703.0f);
    private static final float LEFT_EYE_CENTER_Y = Ae2AndroidDimenUtil.ae2androidY(608.0f);
    private static final float LEFT_EYE_BOTTOM_X = Ae2AndroidDimenUtil.ae2androidX(703.5f);
    private static final float LEFT_EYE_BOTTOM_Y = Ae2AndroidDimenUtil.ae2androidY(790.5f);

    @Override
    protected String eyeName() {
        return "LeftEye";
    }

    @Override
    protected float eyeCenterX() {
        return LEFT_EYE_CENTER_X;
    }

    @Override
    protected float eyeCenterY() {
        return LEFT_EYE_CENTER_Y;
    }

    @Override
    protected float eyeBottomX() {
        return LEFT_EYE_BOTTOM_X;
    }

    @Override
    protected float eyeBottomY() {
        return LEFT_EYE_BOTTOM_Y;
    }

    @Override
    protected Bitmap eyeBitmapBottom(@NonNull Context context) {
        return ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_left_bottom))).getBitmap();
    }

    @Override
    protected Bitmap eyeBitmapOut(@NonNull Context context) {
        return ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_left_out))).getBitmap();
    }

    @Override
    protected Bitmap eyeBitmapIn(@NonNull Context context) {
        return ((BitmapDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.mipmap.eye_left_in))).getBitmap();
    }
}
