package org.goose.core;

import com.raylib.java.core.rCore;

public class Time {
    private static double lastTime = rCore.GetTime();

    private static double currentTime = 0;

    public static double getLastTime() {
        return lastTime;
    }

    public static void setLastTime(double lastTime) {
        Time.lastTime = lastTime;
    }

    public static double getCurrentTime() {
        return currentTime;
    }

    public static void setCurrentTime(double currentTime) {
        Time.currentTime = currentTime;
    }

    public static double getDeltaTime() {
        return (currentTime-lastTime);
    }

    /**
     * Returns the current time
     */
    public static double now() {
        return rCore.GetTime();
    }

    /**
     * Waits a certain amount of time
     */
    public static void sleep(double time) {
        Renderer.renderer.core.WaitTime((float)time);
    }
}
