package org.goose.objects;

import com.raylib.java.raymath.Vector2;
import com.raylib.java.shapes.Rectangle;
import com.studiohartman.jamepad.ControllerUnpluggedException;

public abstract class Entity {
    //players, mobs, special things that may need this
    private boolean physicsBound = false;

    private Rectangle rect;

    private Vector2 position;
    private Vector2 velocity = new Vector2();

    public Entity() {}

    /**
     * Called every frame rendering cycle, handles things like drawing and GUI elements
     */
    public abstract void render();

    public Rectangle getRect() {
        return this.rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    /**
     * Ticks the entity, used for game calculations
     * @param delta
     */
    public abstract void tick(double delta); //used for physics

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    /**
     * Returns velocity of the entity as a vector2
     * @return
     */
    public Vector2 getVelocity() {
        return velocity;
    }

    /**
     * Sets the velocity of an entity as a vector2
     * @param velocity
     */
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
