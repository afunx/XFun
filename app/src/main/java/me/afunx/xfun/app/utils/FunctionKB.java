package me.afunx.xfun.app.utils;

import androidx.annotation.NonNull;

/**
 * 直线方程：y = kx + b
 */
public class FunctionKB {

    public float k;

    public float b;

    FunctionKB(float k, float b) {
        this.k = k;
        this.b = b;
    }

    /**
     * 根据x，求解y
     *
     * @param x x
     * @return y = kx + b
     */
    public float calculateY(float x) {
        return this.k * x + this.b;
    }

    /**
     * 根据y，求解x
     *
     * @param y y
     * @return x = (y-b)/k
     */
    public float calculateX(float y) {
        return (y - this.b) / this.k;
    }

    @NonNull
    @Override
    public String toString() {
        return "FunctionKB{" +
                "k=" + k +
                ", b=" + b +
                '}';
    }
}
