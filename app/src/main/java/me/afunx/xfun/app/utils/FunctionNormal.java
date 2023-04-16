package me.afunx.xfun.app.utils;

/**
 * 法线方程：y-y0 = (-1/k) * (x-x0)
 * (k!=0)
 */
public class FunctionNormal {

    private float k;

    private float x0;

    private float y0;

    FunctionNormal(float k, float x0, float y0) {
        this.k = k;
        this.x0 = x0;
        this.y0 = y0;
    }

    /**
     * 根据x，求解y
     *
     * @param x x
     * @return y = (-1/k) * (x-x0) + y0
     */
    public float calculateY(float x) {
        return (-1 / this.k) * (x - this.x0) + this.y0;
    }

    /**
     * 根据y，求解x
     * y-y0 = (-1/k) * (x-x0)
     *
     * @param y y
     * @return x = -k * (y-y0) + x0
     */
    public float calculateX(float y) {
        return -1 * this.k * (y-this.y0) + this.x0;
    }

    @Override
    public String toString() {
        return "FunctionNormal{" +
                "k=" + k +
                ", x0=" + x0 +
                ", y0=" + y0 +
                '}';
    }
}
