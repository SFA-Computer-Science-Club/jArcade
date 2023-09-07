package org.goose.core;

import org.lwjgl.glfw.GLFW;

public class Time {

    private static double lastTime = GLFW.glfwGetTime();

    public static double getLastTime() {
        return lastTime;
    }

    public static void setLastTime(double lastTime) {
        Time.lastTime = lastTime;
    }

    public static double getCurrentTime() {
        return GLFW.glfwGetTime();
    }

    public static double getDeltaTime() {
        return (getCurrentTime()-getLastTime());
    }

    /**
     * Returns the current time in milliseconds
     */
    public static double now() {
        return GLFW.glfwGetTime()*1000;
    }

    /**
     * Waits a certain amount of time
     */
    public static void sleep(double time) {
        Renderer.renderer.core.WaitTime((float)time);
    }
}
