package org.goose.objects;

import com.raylib.java.core.Color;
import com.raylib.java.shapes.Rectangle;
import com.raylib.java.textures.rTextures;
import com.raylib.java.textures.Texture2D;
import org.goose.Main;
import org.goose.core.Renderer;
import org.goose.core.TextureLoader;

import java.io.File;
import java.net.URISyntaxException;

public class DirtBlock extends Tile {

    private static Texture2D texture;
    private Rectangle rect;

    public static void initTexture() {
        System.out.println(Main.class.getClassLoader().getResource("textures/dirt_block.png").getPath());
        //texture = new Texture2D("/textures/dirt_block.png");
        texture = TextureLoader.loadTexture("textures/dirt_block.png");
        texture.setHeight(32);
        texture.setWidth(32);
    }


    public DirtBlock() {
        setSizeX(32);
        setSizeY(32);
    }

    public DirtBlock(int x, int y, int size) {
        rect = new Rectangle(x, y, size, size);
        //texture.setWidth(size);
        //texture.setHeight(size);
        setSizeY(size);
        setSizeX(size);
        setX(x);
        setY(y);
    }

    @Override
    public void render() {
        //x and y given in this format are 0,1,2,3,4, we have to convert them to int and scale up
        //Renderer.drawTile(getX(),getY(),getSizeX(), Color.BROWN);
        Renderer.renderer.textures.DrawTexture(texture, getX(), getY(), Color.WHITE);
    }
}
