package org.goose.core.sound;

import com.raylib.java.raudioal.Music;
import com.raylib.java.raudioal.Sound;
import com.raylib.java.raudioal.Wave;
import org.goose.Main;
import org.goose.core.Renderer;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisAlloc;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.EXTFloat32.AL_FORMAT_MONO_FLOAT32;
import static org.lwjgl.openal.EXTFloat32.AL_FORMAT_STEREO_FLOAT32;
import static org.lwjgl.stb.STBVorbis.*;

public class SoundLoader {
    public static byte[] fileReader(String path) {
        byte[] data = null;

        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(path);

        if (inputStream != null) {
            try {
                data = new byte[inputStream.available()];
                inputStream.read(data);
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
        }
        return data;
    }

    public static Wave LoadWave(String path) {
        byte[] data = fileReader(path);
        ByteBuffer byteBufferData = ByteBuffer.wrap(data);
        return Renderer.renderer.audio.LoadWaveEx(byteBufferData, 0,44100,16,1);
    }

    public static Audio LoadSound(String path) {
        byte[] data = fileReader(path);
        return LoadSoundFromWave(loadWave(data));
    }

    public static Music LoadMusic(String path) {
        return Renderer.renderer.audio.LoadMusicStream(path);
    }

    //NO MANS LAND//
    public static Audio LoadSoundFromWave(SoundWave wave) {
        Audio audio = new Audio();

        if (wave.data == null) {
            System.out.println("Error in loading sound, wave data is null!");
            return null;
        }
        int format = 0;
        if (wave.channels == 1) {
            switch (wave.sampleSize) {
                case 8 -> format = AL_FORMAT_MONO8;
                case 16 -> format = AL_FORMAT_MONO16;
                case 32 -> format = AL_FORMAT_MONO_FLOAT32;
                // Requires OpenAL extension: AL_EXT_FLOAT32
                default -> System.out.println("Wave size not supported: " + wave.sampleSize);
            }
        } else if (wave.channels == 2) {
            switch (wave.sampleSize) {
                case 8 -> format = AL_FORMAT_STEREO8;
                case 16 -> format = AL_FORMAT_STEREO16;
                case 32 -> format = AL_FORMAT_STEREO_FLOAT32;
                // Requires OpenAL extension: AL_EXT_FLOAT32
                default -> System.out.println("Wave size not supported: " + wave.sampleSize);
            }
        }

        int source = alGenSources();
        alSourcef(source, AL_PITCH, 1.0f);
        alSourcef(source, AL_GAIN, 1.0f);
        alSource3f(source, AL_POSITION, 0,0,0);
        alSource3f(source, AL_VELOCITY, 0,0,0);
        alSourcei(source, AL_LOOPING, AL_FALSE);

        int buffer = alGenBuffers();
        int dataSize = wave.sampleCount * wave.channels * (wave.sampleSize / 8);

        if (wave.data instanceof ByteBuffer) {
            alBufferData(buffer, format, ((ByteBuffer) wave.data), wave.sampleRate);
        } else if (wave.data instanceof ShortBuffer) {
            alBufferData(buffer, format, ((ShortBuffer) wave.data), wave.sampleRate);
        }

        alSourcei(source, AL_BUFFER, buffer);

        audio.setLength((double)wave.sampleCount/wave.sampleRate);
        audio.setSource(source);
        audio.setBuffer(buffer);
        audio.setFormat(format);

        return audio;
    }


    public static SoundWave loadWave(byte[] fileData) {
        SoundWave wave = new SoundWave();

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer errorBuffer = stack.mallocInt(1);
            IntBuffer ChannelsBuffer = stack.mallocInt(1);
            IntBuffer SamplesBuffer = stack.mallocInt(1);
            ByteBuffer dataBuffer = ByteBuffer.allocateDirect(fileData.length);
            dataBuffer.put(fileData).flip();

            long oggData = stb_vorbis_open_memory(dataBuffer, errorBuffer, null);

            if(oggData != 0) {
                ByteBuffer infoBuffer = ByteBuffer.allocateDirect(STBVorbisInfo.SIZEOF);
                STBVorbisInfo vorbisInfo = new STBVorbisInfo(infoBuffer);
                stb_vorbis_get_info(oggData, vorbisInfo);

                ShortBuffer oggAudio = stb_vorbis_decode_memory(dataBuffer, ChannelsBuffer, SamplesBuffer);
                wave.sampleRate = SamplesBuffer.get(0);
                wave.sampleSize = 16;
                wave.channels = ChannelsBuffer.get(0);
                wave.sampleCount = stb_vorbis_stream_length_in_samples(oggData);
                wave.data = oggAudio;

                stb_vorbis_close(oggData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return wave;
    }
}
