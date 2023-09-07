package org.goose.objects;

import com.raylib.java.core.Color;
import com.raylib.java.core.input.Keyboard;
import com.raylib.java.shapes.Rectangle;
import org.goose.core.Input;
import org.goose.core.Renderer;

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
        if (Input.pressedKeys.contains(Keyboard.KEY_A)) {
            this.getRect().x -= 3;
        }
        if (Input.pressedKeys.contains(Keyboard.KEY_D)) {
            this.getRect().x += 3;
        }
        //this.getRect().y += 2;
    }
}
