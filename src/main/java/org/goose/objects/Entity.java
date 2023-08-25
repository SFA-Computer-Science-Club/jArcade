package org.goose.objects;

import com.raylib.java.shapes.Rectangle;

public abstract class Entity {
    //players, mobs, special things that may need this

    private Rectangle rect;

    public Entity() {}

    public abstract void render();

    public abstract void tick(); //used for physics
}
