package org.goose.core;

import com.raylib.java.core.input.Keyboard;
import com.raylib.java.raymath.Vector2;
import com.raylib.java.core.rCore;

import java.util.ArrayList;

public class Input {
    //input class used to help simplify user input

    /**
     * Will be cleared after the first update of a game loop, only runs once
     */
    public static ArrayList<Integer> pressedKeys = new ArrayList<>();
    public static ArrayList<Integer> heldKeys = new ArrayList<>();

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
        return rCore.GetMousePosition();
    }

    public static void registerInput() {
        pressedKeys.clear();
        heldKeys.clear();
        //called before each frame to get the input
        for (int i = 31; i < 337; i++) {
            if (isKeyHeld(i)) {
                heldKeys.add(i);
            }
            if (isKeyPressed(i)) {
                pressedKeys.add(i);
            }
        }
    }
}
