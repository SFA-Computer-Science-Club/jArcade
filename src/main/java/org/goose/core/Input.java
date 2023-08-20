package org.goose.core;

import com.raylib.java.core.input.Keyboard;
import com.raylib.java.raymath.Vector2;

public class Input {
    //input class used to help simplify user input
    public static boolean isKeyHeld(int key) {
        if (!Renderer.renderer.core.IsKeyUp(key)) {
            //key is held down
            return true;
        }
        return false;
    }

    public static boolean isKeyPressed(int key) {
        if (Renderer.renderer.core.IsKeyPressed(key)) {
            return true;
        }
        return false;
    }

    public static Vector2 getMousePosition() {
        return Renderer.renderer.core.GetMouseDelta();
    }

}
