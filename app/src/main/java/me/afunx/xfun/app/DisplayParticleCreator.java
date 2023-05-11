package me.afunx.xfun.app;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.afunx.xfun.common.utils.LogUtils;

import java.util.List;

public class DisplayParticleCreator {

    private static final String TAG = "DisplayParticleCreator";

    private static final boolean DEBUG = true;

    public static void createParticles(@NonNull List<DisplayParticle> displayParticleList) {
        displayParticleList.clear();
        // 右融合
        createParticlesRight(displayParticleList);
        // 左融合
        createParticlesLeft(displayParticleList);
        // 裂变
        createParticlesFission(displayParticleList);
    }

    private static void createParticlesRight(@NonNull List<DisplayParticle> displayParticleList) {
        // 1
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(2000.0f)
                        .setStartY(733.7f)
                        .setEndX(1182.8f)
                        .setEndY(691.5f)
                        .setRadius(30.0f)
                        .setInterval(16 * 1000L)
                        .setStartTime("15:12")
                        .setEndTime("17:05")
                        .setEntranceTime("00:00")
                        .setExitTime("16:00")
                        .setClipStartTime("01:08")
                        .build());
        // 2
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(900.0f)
                        .setStartY(1273.7f)
                        .setEndX(1182.8f)
                        .setEndY(691.5f)
                        .setRadius(20.0f)
                        .setInterval(16 * 1000L)
                        .setStartTime("13:00")
                        .setEndTime("14:17")
                        .setEntranceTime("00:00")
                        .setExitTime("16:00")
                        .setClipStartTime("01:08")
                        .build());
        // 3
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(2020.0f)
                        .setStartY(893.7f)
                        .setEndX(1242.8f)
                        .setEndY(691.5f)
                        .setRadius(32.5f)
                        .setInterval(16 * 1000L)
                        .setStartTime("11:19")
                        .setEndTime("13:12")
                        .setEntranceTime("00:00")
                        .setExitTime("16:00")
                        .setClipStartTime("01:08")
                        .build());
        // 4
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(2000.0f)
                        .setStartY(733.7f)
                        .setEndX(1182.8f)
                        .setEndY(691.5f)
                        .setRadius(30.0f)
                        .setInterval(16 * 1000L)
                        .setStartTime("10:14")
                        .setEndTime("12:07")
                        .setEntranceTime("00:00")
                        .setExitTime("16:00")
                        .setClipStartTime("01:08")
                        .build());
        // 5
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(900.0f)
                        .setStartY(1273.7f)
                        .setEndX(1182.8f)
                        .setEndY(619.5f)
                        .setRadius(20.0f)
                        .setInterval(16 * 1000L)
                        .setStartTime("08:02")
                        .setEndTime("09:19")
                        .setEntranceTime("00:00")
                        .setExitTime("16:00")
                        .setClipStartTime("01:08")
                        .build());
        // 6
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(2020.0f)
                        .setStartY(893.7f)
                        .setEndX(1242.8f)
                        .setEndY(691.5f)
                        .setRadius(32.5f)
                        .setInterval(16 * 1000L)
                        .setStartTime("06:21")
                        .setEndTime("08:14")
                        .setEntranceTime("00:00")
                        .setExitTime("16:00")
                        .setClipStartTime("01:08")
                        .build());

        // 7
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(2000.0f)
                        .setStartY(733.7f)
                        .setEndX(1182.8f)
                        .setEndY(691.5f)
                        .setRadius(30.0f)
                        .setInterval(16 * 1000L)
                        .setStartTime("05:18")
                        .setEndTime("07:11")
                        .setEntranceTime("00:00")
                        .setExitTime("16:00")
                        .setClipStartTime("01:08")
                        .build());

        // 8
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(900.0f)
                        .setStartY(1273.7f)
                        .setEndX(1182.8f)
                        .setEndY(691.5f)
                        .setRadius(20.0f)
                        .setInterval(16 * 1000L)
                        .setStartTime("03:06")
                        .setEndTime("04:23")
                        .setEntranceTime("00:00")
                        .setExitTime("16:00")
                        .setClipStartTime("01:08")
                        .build());

        // 9
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(2020.0f)
                        .setStartY(893.7f)
                        .setEndX(1242.8f)
                        .setEndY(691.5f)
                        .setRadius(32.5f)
                        .setInterval(16 * 1000L)
                        .setStartTime("02:01")
                        .setEndTime("03:18")
                        .setEntranceTime("00:00")
                        .setExitTime("16:00")
                        .setClipStartTime("01:08")
                        .build());
    }

    private static void createParticlesLeft(List<DisplayParticle> displayParticleList) {
        //  1
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(-62.7f)
                        .setStartY(883.7f)
                        .setEndX(662.8f)
                        .setEndY(691.5f)
                        .setRadius(40.0f)
                        .setInterval(16 * 1000L)
                        .setStartTime("13:22")
                        .setEndTime("15:15")
                        .setEntranceTime("00:00")
                        .setExitTime("16:00")
                        .setClipStartTime("01:08")
                        .build());
        //  2
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(1117.3f)
                        .setStartY(1343.7f)
                        .setEndX(662.8f)
                        .setEndY(691.5f)
                        .setRadius(40.0f)
                        .setInterval(16 * 1000L)
                        .setStartTime("12:11")
                        .setEndTime("14:04")
                        .setEntranceTime("00:00")
                        .setExitTime("16:00")
                        .setClipStartTime("01:08")
                        .build());
        //  3
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(177.3f)
                        .setStartY(1363.7f)
                        .setEndX(662.8f)
                        .setEndY(691.5f)
                        .setRadius(40.0f)
                        .setInterval(16 * 1000L)
                        .setStartTime("11:02")
                        .setEndTime("12:19")
                        .setEntranceTime("00:00")
                        .setExitTime("16:00")
                        .setClipStartTime("01:08")
                        .build());
        //  4
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(-62.7f)
                        .setStartY(883.7f)
                        .setEndX(662.8f)
                        .setEndY(691.5f)
                        .setRadius(40.0f)
                        .setInterval(16 * 1000L)
                        .setStartTime("09:00")
                        .setEndTime("10:17")
                        .setEntranceTime("00:00")
                        .setExitTime("16:00")
                        .setClipStartTime("01:08")
                        .build());
        //  5
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(1117.3f)
                        .setStartY(1343.7f)
                        .setEndX(662.8f)
                        .setEndY(691.5f)
                        .setRadius(40.0f)
                        .setInterval(16 * 1000L)
                        .setStartTime("07:13")
                        .setEndTime("09:06")
                        .setEntranceTime("00:00")
                        .setExitTime("16:00")
                        .setClipStartTime("01:08")
                        .build());
        //  6
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(177.3f)
                        .setStartY(1363.7f)
                        .setEndX(662.8f)
                        .setEndY(691.5f)
                        .setRadius(40.0f)
                        .setInterval(16 * 1000L)
                        .setStartTime("06:04")
                        .setEndTime("07:21")
                        .setEntranceTime("00:00")
                        .setExitTime("16:00")
                        .setClipStartTime("01:08")
                        .build());
        //  7
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(-62.7f)
                        .setStartY(883.7f)
                        .setEndX(662.8f)
                        .setEndY(691.5f)
                        .setRadius(40.0f)
                        .setInterval(16 * 1000L)
                        .setStartTime("04:04")
                        .setEndTime("05:21")
                        .setEntranceTime("00:00")
                        .setExitTime("16:00")
                        .setClipStartTime("01:08")
                        .build());
        //  8
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(1117.3f)
                        .setStartY(1343.7f)
                        .setEndX(662.8f)
                        .setEndY(691.5f)
                        .setRadius(40.0f)
                        .setInterval(16 * 1000L)
                        .setStartTime("02:17")
                        .setEndTime("04:10")
                        .setEntranceTime("00:00")
                        .setExitTime("16:00")
                        .setClipStartTime("01:08")
                        .build());

        //  9
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(177.3f)
                        .setStartY(1363.7f)
                        .setEndX(662.8f)
                        .setEndY(691.5f)
                        .setRadius(40.0f)
                        .setInterval(16 * 1000L)
                        .setStartTime("01:08")
                        .setEndTime("03:01")
                        .setEntranceTime("00:00")
                        .setExitTime("16:00")
                        .setClipStartTime("01:08")
                        .build());
    }

    private static void createParticlesFission(List<DisplayParticle> displayParticleList) {
        int size = displayParticleList.size();
        for (int i = 0; i < size; i++) {
            DisplayParticle particle = displayParticleList.get(i);
            displayParticleList.add(particle.clone("02:22", "16:00"));
            displayParticleList.add(particle.clone("00:21", "16:00"));
        }
        for (int i = 0; i<displayParticleList.size(); i++) {
            displayParticleList.get(i)._idx = i;
            if (i == 6) {
                displayParticleList.get(i)._color = Color.parseColor("#FF00FF00");
            }
        }
        if (DEBUG) {
            for (int i = 0; i < displayParticleList.size(); i++) {
                DisplayParticle particle = displayParticleList.get(i);
                LogUtils.i(TAG, i + " :: " + particle.toString());
            }
        }
    }
}
