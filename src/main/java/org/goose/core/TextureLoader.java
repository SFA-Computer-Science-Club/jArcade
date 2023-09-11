package org.goose.core;

import com.raylib.java.textures.Image;
import com.raylib.java.textures.Texture2D;
import com.raylib.java.textures.rTextures;
import org.goose.Main;

import java.io.InputStream;

public class TextureLoader {
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

    public static Texture2D loadTexture(String path) {
        byte[] data = fileReader(path);
        Image image = rTextures.LoadImageFromMemory(path, data, 0);

        if (image.getData() == null) {
            System.out.println("ERROR IN TEXTURE LOADING");
        }
        Texture2D texture = rTextures.LoadTextureFromImage(image);
        rTextures.UnloadImage(image);
        return texture;
    }

    public static Image loadImage(String path) {
        byte[] data = fileReader(path);
        Image image = rTextures.LoadImageFromMemory(path, data, 0);

        if (image.getData() == null) {
            System.out.println("ERROR IN image LOADING");
        }
        return image;
    }
}
