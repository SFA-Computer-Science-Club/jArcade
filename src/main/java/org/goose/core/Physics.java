package org.goose.core;

import com.raylib.java.core.input.Keyboard;
import org.goose.Main;

public class Physics {

    public static void tick(double deltaTime) {
        //pass physics to world, world passes to entities etc
        if (Input.isKeyPressed(Keyboard.KEY_F3)) {
            Renderer.drawFPS = !Renderer.drawFPS;
        }

        Main.world.tick(deltaTime);
    }
}
