package org.goose.objects;

//representative of a tile

public abstract class Tile {

    private int x;
    private int y;
    private int sizeX;
    private int sizeY;

    private boolean gravity;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
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

    public abstract void render();
}
