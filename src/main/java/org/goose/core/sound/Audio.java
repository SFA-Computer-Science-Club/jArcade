package org.goose.core.sound;

import static org.lwjgl.openal.AL10.alSourcePlay;

public class Audio {
    private boolean looped;
    private double currentTime;
    private double length;

    //stuff used for the audio
    private int source;
    private int buffer;
    private int format;

    public Audio(String path) {

    }

    public Audio(){}

    public void play() {
        alSourcePlay(this.source);
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
    public boolean isLooped() {
        return looped;
    }

    public void setLooped(boolean looped) {
        this.looped = looped;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getBuffer() {
        return buffer;
    }

    public void setBuffer(int buffer) {
        this.buffer = buffer;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }
}
