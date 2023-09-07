package org.goose.core;

import com.raylib.java.core.input.Keyboard;
import com.raylib.java.raymath.Vector2;

import java.util.ArrayList;

public class Input {
    //input class used to help simplify user input

    public static ArrayList<Integer> pressedKeys = new ArrayList<>();

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

    public static void registerInput() {
        pressedKeys.clear();
        //called before each frame to get the input
        if (isKeyPressed(Keyboard.KEY_A) || isKeyHeld(Keyboard.KEY_A)) {
            pressedKeys.add(Keyboard.KEY_A);
        }
        if (isKeyPressed(Keyboard.KEY_D) || isKeyHeld(Keyboard.KEY_D)) {
            pressedKeys.add(Keyboard.KEY_D);
        }
    }
}
