package org.goose.objects;

import com.raylib.java.core.Color;
import com.raylib.java.shapes.Rectangle;
import com.raylib.java.shapes.rShapes;
import com.raylib.java.textures.Texture2D;
import org.goose.core.Renderer;
import org.goose.core.TextureLoader;
import org.goose.core.event.core.EventHandler;
import org.goose.core.event.core.ListenerPriority;
import org.goose.core.event.events.core.RenderBeginEvent;
import org.goose.core.event.events.core.TickEvent;
import org.goose.level.Level;

public class SFACube extends Tile{
    public static Texture2D texture;

    public SFACube(int x, int y, int size, Level level) {
        this.setRect(new Rectangle(x, y, size, size));
        setLevel(level);
        setSizeY(size);
        setSizeX(size);
        setX(x);
        setY(y);
    }

    public static void initTexture() {
        texture = TextureLoader.loadTexture("textures/sfa_cube.png");
        texture.setHeight(Renderer.getWindowWidth()/32);
        texture.setWidth(Renderer.getWindowWidth()/32);
    }

    public void render() {
        if (getLevel().isEnabled()) {
            Renderer.renderer.textures.DrawTexture(texture, getX(), getY(), Color.WHITE);
        }
    }

    public void handlePhysics() {
        if (getLevel().isEnabled()) {
            this.setX((int) (this.getX() + this.velocity.x));
            this.setY((int) (this.getY() + this.velocity.y));
        }
    }
}
