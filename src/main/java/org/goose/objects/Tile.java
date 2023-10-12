package org.goose.objects;

//representative of a tile

import com.raylib.java.raymath.Vector2;
import com.raylib.java.shapes.Rectangle;
import org.goose.core.event.core.EventHandler;
import org.goose.core.event.core.EventListener;
import org.goose.core.event.core.EventManager;
import org.goose.core.event.events.core.TickEvent;
import org.goose.level.Level;

public abstract class Tile{

    public int x;
    public int y;
    private int sizeX;
    private int sizeY;
    private Level level;
    public Vector2 velocity = new Vector2();
    private Rectangle rect;
    private boolean canCollide;

    private boolean gravity;

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(int x, int y) {
        this.velocity.x = x;
        this.velocity.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.rect.x = x;
        this.x = x;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.rect.y = y;
        this.y = y;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public boolean isGravity() {
        return gravity;
    }

    public void setGravity(boolean gravity) {
        this.gravity = gravity;
    }

    public boolean isCanCollide() {
        return canCollide;
    }

    public void setCanCollide(boolean canCollide) {
        this.canCollide = canCollide;
    }
}
