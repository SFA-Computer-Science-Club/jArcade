package org.goose.objects;

import com.raylib.java.core.Color;
import com.raylib.java.core.input.Keyboard;
import com.raylib.java.shapes.Rectangle;
import org.goose.Main;
import org.goose.core.Input;
import org.goose.core.Physics;
import org.goose.core.Renderer;

import java.util.ArrayList;

public class Player extends Entity{

    public Player() {
        super.setPhysicsBound(true);
        super.setRect(new Rectangle(0,0,10,20));
    }

    public Player(int x, int y) {
        super.setPhysicsBound(true);
        super.setRect(new Rectangle(x,y,20,50));
    }


    @Override
    public void render() {
        Renderer.renderer.shapes.DrawRectangle((int)getRect().x, (int)getRect().y, (int)getRect().width, (int)getRect().height, Color.WHITE);
    }

    @Override
    public void tick(double delta) {
        //do things like collision detecting, gravity, etc here
        if (Input.pressedKeys.contains(Keyboard.KEY_A) || Input.heldKeys.contains(Keyboard.KEY_A)) {
            this.getVelocity().x -= 8;
        }
        if (Input.pressedKeys.contains(Keyboard.KEY_D) || Input.heldKeys.contains(Keyboard.KEY_D)) {
            this.getVelocity().x += 8;
        }

        this.getVelocity().y += (float)(Main.world.getGravity());

        if (Input.pressedKeys.contains(Keyboard.KEY_SPACE)) {
            this.getVelocity().y = -35;
            Input.pressedKeys.remove((Integer) Keyboard.KEY_SPACE);
        }

        if (this.getVelocity().x > 8) {
            this.getVelocity().x = 8;
        } else if (this.getVelocity().x <-8) {
            this.getVelocity().x = -8;
        }

        //do friction
        if (this.getVelocity().x < 0 && !Input.heldKeys.contains(Keyboard.KEY_A)) {
            this.getVelocity().x += 0.1;
            if (Math.abs(this.getVelocity().x) < 0.1) {
                this.getVelocity().x = 0;
            }
        }
        if (this.getVelocity().x > 0 && !Input.heldKeys.contains(Keyboard.KEY_D)) {
            this.getVelocity().x -= 0.1;
            if (Math.abs(this.getVelocity().x) < 0.1) {
                this.getVelocity().x = 0;
            }
        }

        //do gravity

        if (this.getVelocity().y > 500) {
            this.getVelocity().y = 500;
        }

        //Add the rect position with the velocity

        //Rectangle newRect = new Rectangle(new Vector2(this.getRect().x, this.getRect().y), this.getRect().width, this.getRect().height);

        this.getRect().y += this.getVelocity().y * (delta/100);
        ArrayList<Tile> verticalCollisions = Physics.entityCollided(this);

        if (verticalCollisions.size() != 0) {
            //we did collide with something vertically
            if (verticalCollisions.size()==1) {
                Tile collided = verticalCollisions.get(0);

                if ((this.getRect().y - collided.getRect().y) > 0) {
                    //player below
                    this.getRect().y = collided.getRect().y + collided.getRect().height;
                    if (this.getVelocity().y < 0) {
                        this.getVelocity().y = 0;
                    }
                } else {
                    this.getRect().y = collided.getRect().y - this.getRect().height;
                    this.getVelocity().y = 0;
                }
            } else {
                double playerCenterY = (this.getRect().y + (this.getRect().height/2));
                Tile closestTile = null;
                double closestNumber = 0;
                for (Tile tile : verticalCollisions) {
                    double centerY = (tile.getRect().y + (tile.getRect().height/2));
                    if (closestTile == null) {
                        closestTile = tile;
                        closestNumber = centerY - playerCenterY;
                    }
                    if (Math.abs(centerY - playerCenterY) < closestNumber) {
                        closestTile = tile;
                    }
                }
                if (playerCenterY - (closestTile.getRect().y+(closestTile.getRect().height/2)) > 0) {
                    this.getRect().y = closestTile.getRect().y + closestTile.getRect().getHeight();
                    this.getVelocity().y = 0;
                } else {
                    this.getVelocity().y = 0;
                    this.getRect().y = closestTile.getRect().y - this.getRect().height;
                }
            }
        }

        //now resolve horizontal collisions

        this.getRect().x += this.getVelocity().x * (delta/25);

        ArrayList<Tile> horizontalCollisions = Physics.entityCollided(this);
        if (horizontalCollisions.size() != 0) {
            if (horizontalCollisions.size() == 1) {
                if (this.getRect().x - horizontalCollisions.get(0).getRect().x > 0) {
                    //player is to the right
                    Tile tile = horizontalCollisions.get(0);
                    this.getRect().x = tile.getRect().x + tile.getRect().width;
                    this.getVelocity().x = 0;
                } else {
                    Tile tile = horizontalCollisions.get(0);
                    this.getRect().x = tile.getRect().x - this.getRect().getWidth();
                    this.getVelocity().x = 0;
                }
            } else {
                double playerCenterX = (this.getRect().x + (this.getRect().width/2));
                Tile closestTile = null;
                double closestNumber = 0;

                for (Tile tile : horizontalCollisions) {
                    double tileCenterX = tile.getRect().x + (tile.getRect().width/2);
                    if (closestTile == null) {
                        closestTile = tile;
                        closestNumber = Math.abs(tileCenterX - playerCenterX);
                    }
                    if (Math.abs(tileCenterX - playerCenterX) < closestNumber) {
                        closestTile = tile;
                        closestNumber = Math.abs(tileCenterX - playerCenterX);
                    }
                }
                double closestCenterX = closestTile.getRect().x + (closestTile.getRect().width/2);
                if (playerCenterX - closestCenterX > 0) {
                    this.getRect().x = closestTile.getRect().x + closestTile.getRect().width;
                    this.getVelocity().x = 0;
                } else {
                    this.getRect().x = closestTile.getRect().x - this.getRect().width;
                    this.getVelocity().x = 0;
                }
            }
        }

    }
}
