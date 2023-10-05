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

    public static Sound LoadSound(String path) {
        byte[] data = fileReader(path);
        return Renderer.renderer.audio.LoadSoundFromWave(loadWave(data));
    }

    public static Music LoadMusic(String path) {
        return Renderer.renderer.audio.LoadMusicStream(path);
    }

    //NO MANS LAND//
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

                System.out.println(wave.sampleCount);
                System.out.println(wave.sampleRate);
                System.out.println(wave.channels);

                stb_vorbis_close(oggData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return wave;
    }
}
