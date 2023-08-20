package org.goose.objects;

import com.raylib.java.core.Color;
import com.raylib.java.shapes.Rectangle;
import com.raylib.java.textures.Texture2D;
import org.goose.core.Renderer;

public class GrassBlock extends Tile {

    //private static Texture2D texture;
    private Rectangle rect;

    public static void initTexture() {
        //texture = new Texture2D("/textures/grass_block.png");
        //.setWidth(32);
        //texture.setHeight(32);
    }

    public GrassBlock() {
        setSizeX(32);
        setSizeY(32);
    }

    public GrassBlock(int x, int y, int size) {
        rect = new Rectangle(x, y, size, size);
        setSizeY(size);
        setSizeX(size);
        setX(x);
        setY(y);
    }

    @Override
    public void render() {
        //x and y given in this format are 0,1,2,3,4, we have to convert them to int and scale up
        //Renderer.drawTile(getX(),getY(),getSizeX(), Color.BROWN);
        //Renderer.renderer.textures.DrawTexture(texture, getX(), getY(), Color.WHITE);
    }
}
