package me.afunx.xfun.app;

import androidx.annotation.NonNull;

import java.util.List;

public class displayParticleCreator {

    public static void createParticles(@NonNull List<DisplayParticle> displayParticleList) {
        displayParticleList.clear();
        // 右融合
        createParticlesRight(displayParticleList);
        // 左融合
        createParticlesLeft(displayParticleList);
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
                        .setInterval(20 * 1000L)
                        .setStartTime(DisplayParticle.parseTime("15:12"))
                        .setEndTime(DisplayParticle.parseTime("17:05"))
                        .build());
        // 2
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(900.0f)
                        .setStartY(1273.7f)
                        .setEndX(1182.8f)
                        .setEndY(691.5f)
                        .setRadius(20.0f)
                        .setInterval(20 * 1000L)
                        .setStartTime(DisplayParticle.parseTime("13:00"))
                        .setEndTime(DisplayParticle.parseTime("14:17"))
                        .build());
        // 3
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(2020.0f)
                        .setStartY(893.7f)
                        .setEndX(1242.8f)
                        .setEndY(691.5f)
                        .setRadius(32.5f)
                        .setInterval(20 * 1000L)
                        .setStartTime(DisplayParticle.parseTime("11:19"))
                        .setEndTime(DisplayParticle.parseTime("13:12"))
                        .build());
        // 4
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(2000.0f)
                        .setStartY(733.7f)
                        .setEndX(1182.8f)
                        .setEndY(691.5f)
                        .setRadius(30.0f)
                        .setInterval(20 * 1000L)
                        .setStartTime(DisplayParticle.parseTime("10:14"))
                        .setEndTime(DisplayParticle.parseTime("12:07"))
                        .build());
        // 5
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(900.0f)
                        .setStartY(1273.7f)
                        .setEndX(1182.8f)
                        .setEndY(619.5f)
                        .setRadius(20.0f)
                        .setInterval(20 * 1000L)
                        .setStartTime(DisplayParticle.parseTime("08:02"))
                        .setEndTime(DisplayParticle.parseTime("09:19"))
                        .build());
        // 6
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(2020.0f)
                        .setStartY(893.7f)
                        .setEndX(1242.8f)
                        .setEndY(691.5f)
                        .setRadius(32.5f)
                        .setInterval(20 * 1000L)
                        .setStartTime(DisplayParticle.parseTime("06:21"))
                        .setEndTime(DisplayParticle.parseTime("08:14"))
                        .build());

        // 7
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(2000.0f)
                        .setStartY(733.7f)
                        .setEndX(1182.8f)
                        .setEndY(691.5f)
                        .setRadius(30.0f)
                        .setInterval(20 * 1000L)
                        .setStartTime(DisplayParticle.parseTime("05:18"))
                        .setEndTime(DisplayParticle.parseTime("07:11"))
                        .build());

        // 8
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(900.0f)
                        .setStartY(1273.7f)
                        .setEndX(1182.8f)
                        .setEndY(691.5f)
                        .setRadius(20.0f)
                        .setInterval(20 * 1000L)
                        .setStartTime(DisplayParticle.parseTime("03:06"))
                        .setEndTime(DisplayParticle.parseTime("04:23"))
                        .build());

        // 9
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(2020.0f)
                        .setStartY(893.7f)
                        .setEndX(1242.8f)
                        .setEndY(691.5f)
                        .setRadius(32.5f)
                        .setInterval(20 * 1000L)
                        .setStartTime(DisplayParticle.parseTime("02:01"))
                        .setEndTime(DisplayParticle.parseTime("03:18"))
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
                        .setInterval(20 * 1000L)
                        .setStartTime(DisplayParticle.parseTime("13:22"))
                        .setEndTime(DisplayParticle.parseTime("15:15"))
                        .build());
        //  2
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(1117.3f)
                        .setStartY(1343.7f)
                        .setEndX(662.8f)
                        .setEndY(691.5f)
                        .setRadius(40.0f)
                        .setInterval(20 * 1000L)
                        .setStartTime(DisplayParticle.parseTime("12:11"))
                        .setEndTime(DisplayParticle.parseTime("14:04"))
                        .build());
        //  3
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(177.3f)
                        .setStartY(1363.7f)
                        .setEndX(662.8f)
                        .setEndY(691.5f)
                        .setRadius(40.0f)
                        .setInterval(20 * 1000L)
                        .setStartTime(DisplayParticle.parseTime("11:02"))
                        .setEndTime(DisplayParticle.parseTime("12:19"))
                        .build());
        //  4
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(-62.7f)
                        .setStartY(883.7f)
                        .setEndX(662.8f)
                        .setEndY(691.5f)
                        .setRadius(40.0f)
                        .setInterval(20 * 1000L)
                        .setStartTime(DisplayParticle.parseTime("09:00"))
                        .setEndTime(DisplayParticle.parseTime("10:17"))
                        .build());
        //  5
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(1117.3f)
                        .setStartY(1343.7f)
                        .setEndX(662.8f)
                        .setEndY(691.5f)
                        .setRadius(40.0f)
                        .setInterval(20 * 1000L)
                        .setStartTime(DisplayParticle.parseTime("07:13"))
                        .setEndTime(DisplayParticle.parseTime("09:06"))
                        .build());
        //  6
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(177.3f)
                        .setStartY(1363.7f)
                        .setEndX(662.8f)
                        .setEndY(691.5f)
                        .setRadius(40.0f)
                        .setInterval(20 * 1000L)
                        .setStartTime(DisplayParticle.parseTime("06:04"))
                        .setEndTime(DisplayParticle.parseTime("07:21"))
                        .build());
        //  7
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(-62.7f)
                        .setStartY(883.7f)
                        .setEndX(662.8f)
                        .setEndY(691.5f)
                        .setRadius(40.0f)
                        .setInterval(20 * 1000L)
                        .setStartTime(DisplayParticle.parseTime("04:04"))
                        .setEndTime(DisplayParticle.parseTime("05:21"))
                        .build());
        //  8
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(1117.3f)
                        .setStartY(1343.7f)
                        .setEndX(662.8f)
                        .setEndY(691.5f)
                        .setRadius(40.0f)
                        .setInterval(20 * 1000L)
                        .setStartTime(DisplayParticle.parseTime("02:17"))
                        .setEndTime(DisplayParticle.parseTime("04:10"))
                        .build());
        //  9
        displayParticleList.add(
                new DisplayParticle.Builder()
                        .setStartX(177.3f)
                        .setStartY(1363.7f)
                        .setEndX(662.8f)
                        .setEndY(691.5f)
                        .setRadius(40.0f)
                        .setInterval(20 * 1000L)
                        .setStartTime(DisplayParticle.parseTime("01:08"))
                        .setEndTime(DisplayParticle.parseTime("03:01"))
                        .build());
    }

}
