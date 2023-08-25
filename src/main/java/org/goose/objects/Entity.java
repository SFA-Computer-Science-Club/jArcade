package org.goose.objects;

import com.raylib.java.raymath.Vector2;
import com.raylib.java.shapes.Rectangle;

public abstract class Entity {
    //players, mobs, special things that may need this
    private boolean physicsBound = false;

    private Rectangle rect;

    private Vector2 position;
    private Vector2 velocity;

    public Entity() {}

    public abstract void render();

    public Rectangle getRect() {
        return this.rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public abstract void tick(double delta); //used for physics

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public boolean isPhysicsBound() {
        return physicsBound;
    }

    public void setPhysicsBound(boolean physicsBound) {
        this.physicsBound = physicsBound;
    }
}
