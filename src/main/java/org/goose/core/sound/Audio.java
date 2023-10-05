package org.goose.core.sound;

import static org.lwjgl.openal.AL10.*;

public class Audio {
    private boolean looped;
    private double currentTime;
    private double length;
    private boolean playing;

    //stuff used for the audio
    private int source;
    private int buffer;
    private int format;

    public Audio(String path) {
        Audio sound = SoundLoader.LoadSound(path);
        this.source = sound.source;
        this.buffer = sound.buffer;
        this.format = sound.format;
    }

    public Audio(){}

    public void play() {
        if (playing) {
            int num = alGetSourcei(source, AL_SOURCE_STATE);
            if (num == AL_STOPPED) {
                playing = false;
            }
            return;
        }
        playing = true;
        alSourcePlay(this.source);
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public double getLength() {
        return length;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setLength(double length) {
        this.length = length;
    }
    public boolean isLooped() {
        return looped;
    }

    public void setLooped(boolean looped) {
        if (looped) {
            alSourcei(source, AL_LOOPING, 1);
        } else {
            alSourcei(source, AL_LOOPING, 0);
        }
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
