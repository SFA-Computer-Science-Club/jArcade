package org.goose.objects;

import com.raylib.java.core.Color;
import com.raylib.java.shapes.Rectangle;
import com.raylib.java.textures.Texture2D;
import org.goose.core.Renderer;
import org.goose.core.TextureLoader;

public class DirtBlock extends Tile {

    private static Texture2D texture;

    public static void initTexture() {
        texture = TextureLoader.loadTexture("textures/dirt_block.png");
        texture.setHeight(Renderer.getWindowWidth()/32);
        texture.setWidth(Renderer.getWindowWidth()/32);
    }


    public DirtBlock() {
        setSizeX(32);
        setSizeY(32);
    }

    public DirtBlock(int x, int y, int size) {
        this.setRect(new Rectangle(x, y, size, size));
        setSizeY(size);
        setSizeX(size);
        setX(x);
        setY(y);
    }


    public void render() {
        Renderer.renderer.textures.DrawTexture(texture, getX(), getY(), Color.WHITE);
    }
}
