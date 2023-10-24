package org.goose.level.PongGame;

import com.raylib.java.Config;
import com.raylib.java.core.Color;
import com.raylib.java.core.input.Keyboard;
import com.raylib.java.core.rCore;
import com.raylib.java.raymath.Vector2;
import com.raylib.java.shapes.Rectangle;
import com.raylib.java.shapes.rShapes;
import com.studiohartman.jamepad.ControllerAxis;
import com.studiohartman.jamepad.ControllerButton;
import org.goose.Main;
import org.goose.core.Input;
import org.goose.core.Physics;
import org.goose.core.Renderer;
import org.goose.core.event.core.EventHandler;
import org.goose.core.event.core.EventListener;
import org.goose.core.event.core.EventManager;
import org.goose.core.event.core.ListenerPriority;
import org.goose.core.event.events.core.RenderDrawEvent;
import org.goose.core.event.events.core.TickEvent;
import org.goose.level.Level;
import org.goose.level.MenuScreen;

import java.io.IOException;
import java.util.Random;

public class Pong extends Level {

    private final Rectangle paddle1;
    private final Rectangle paddle2;
    private final Rectangle goal;
    private final Random random = new Random();

    private int speed = 2;

    private double paddle1speed;
    private double paddle2speed;

    private Vector2 goalSpeed;
    private Vector2 score; //x = player1, y = bot

    public Pong() {
        paddle1 = new Rectangle(Renderer.getWindowWidth()/2,Renderer.getWindowHeight()/2, 20, Renderer.getWindowHeight()/5f);
        paddle2 = new Rectangle(Renderer.getWindowWidth()-20, 0, 20, Renderer.getWindowHeight()/5f);
        goal = new Rectangle(Renderer.getWindowWidth()/2f, Renderer.getWindowHeight()/2f, 30,30);
        score = new Vector2(0,0);
        if (random.nextInt(1,3) == 2) {
            goalSpeed = new Vector2(-1, 0);
        } else {
            goalSpeed = new Vector2(1, 0);
        }
    }


    @EventHandler(priority = ListenerPriority.HIGHEST)
    public void render(RenderDrawEvent event) throws IOException {
        Renderer.renderer.core.ClearBackground(Color.BLACK);
        rShapes.DrawRectangleRec(paddle1, Color.WHITE);
        rShapes.DrawRectangleRec(paddle2, Color.WHITE);
        rShapes.DrawRectangleRec(goal, Color.WHITE);
        Renderer.renderer.text.DrawText("Player1: " + (int)score.x + " | " + "Player2: " + (int)score.y, Renderer.getWindowWidth()/2 - 150, 30 * Renderer.getWindowHeight()/1920, 30, Color.WHITE);

    }

    @EventHandler
    public void tick(TickEvent event) {
        if (Input.pressedKeys.contains(Keyboard.KEY_F11)) {
            if (rCore.IsWindowFullscreen()) {
                Renderer.renderer.core.SetWindowState(Config.ConfigFlag.FLAG_WINDOW_MAXIMIZED);
            } else {
                Renderer.renderer.core.ToggleFullscreen();
            }
        }
        if (Input.pressedKeys.contains(Keyboard.KEY_ESCAPE)) {
            this.setEnabled(false);
            for (Level level : Main.worldList) {
                if (level instanceof MenuScreen) {
                    System.out.println(level);
                    level.setEnabled(true);
                    return;
                }
            }
        }
        if (Input.heldKeys.contains(Keyboard.KEY_W)) {
            paddle1speed = -speed;
        }
        if (Input.heldKeys.contains(Keyboard.KEY_S)) {
            paddle1speed = speed;
        }
        if (Input.anyControllersConnected()) {
            try {
                if (Input.controllerManager.getControllerIndex(0).getAxisState(ControllerAxis.LEFTY) > 0.25) {
                    paddle2speed = -speed;
                } else if (Input.controllerManager.getControllerIndex(0).getAxisState(ControllerAxis.LEFTY) < -0.25) {
                    paddle2speed = speed;
                } else {
                    paddle2speed = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (Input.controllerManager.getControllerIndex(1).getAxisState(ControllerAxis.LEFTY) > 0.25) {
                    paddle1speed = -speed;
                } else if (Input.controllerManager.getControllerIndex(1).getAxisState(ControllerAxis.LEFTY) < -0.25) {
                    paddle1speed = speed;
                } else {
                    paddle1speed = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!Input.heldKeys.contains(Keyboard.KEY_W) && !Input.heldKeys.contains(Keyboard.KEY_S)) {
            if (paddle1speed > 0.25) {
                paddle1speed -= .015;
            } else if (paddle1speed < -0.25) {
                paddle1speed += .015;
            } else {
                paddle1speed = 0;
            }
        }
        if (Input.heldKeys.contains(Keyboard.KEY_UP)) {
            paddle2speed = -speed;
        }
        if (Input.heldKeys.contains(Keyboard.KEY_DOWN)) {
            paddle2speed = speed;
        }
        if (!Input.heldKeys.contains(Keyboard.KEY_UP) && !Input.heldKeys.contains(Keyboard.KEY_DOWN)) {
            if (paddle2speed > 0.25) {
                paddle2speed -= .015;
            } else if (paddle2speed < -0.25) {
                paddle2speed += .015;
            } else {
                paddle2speed = 0;
            }
        }

        paddle1.y += paddle1speed;

        if (paddle1.y + paddle1.height > Renderer.getWindowHeight()) {
            paddle1.y = Renderer.getWindowHeight() - paddle1.height;
        }
        if (paddle1.y < 0) {
            paddle1.y = 0;
        }

        if (paddle2.y + paddle2.height > Renderer.getWindowHeight()) {
            paddle2.y = Renderer.getWindowHeight() - paddle2.height;
        }
        if (paddle2.y < 0) {
            paddle2.y = 0;
        }

        paddle2.y += paddle2speed;

        goal.x += goalSpeed.x;
        goal.y += goalSpeed.y;

        if (goal.y + goal.height > Renderer.getWindowHeight()) {
            goalSpeed.y = -goalSpeed.y;
        } else if (goal.y < 0) {
            goalSpeed.y = -goalSpeed.y;
        }

        if (goal.x + goal.width < 0) {
            //player lost
            score.y += 1;
            goal.x = Renderer.getWindowWidth()/2f;
            goal.y = Renderer.getWindowHeight()/2f;
            if (random.nextInt(1,3) == 2) {
                goalSpeed = new Vector2(-1, 0);
            } else {
                goalSpeed = new Vector2(1, 0);
            }
        } else if (goal.x > Renderer.getWindowWidth() + goal.width) {
            score.x += 1;
            goal.x = Renderer.getWindowWidth()/2f;
            goal.y = Renderer.getWindowHeight()/2f;
            if (random.nextInt(1,3) == 2) {
                goalSpeed = new Vector2(-1, 0);
            } else {
                goalSpeed = new Vector2(1, 0);
            }
        }

        if (Physics.rectCollided(paddle1, goal)) {
            if (goal.y > paddle1.y) {
                goal.x += 10;
            } else if (goal.y < paddle1.y + paddle1.height) {
                goal.x += 10;
            }
            goalSpeed.x = 2*-goalSpeed.x;
            if (random.nextInt(1,3) == 2) {
                goalSpeed.y = -speed;
            } else {
                goalSpeed.y = speed;
            }
        }

        if (Physics.rectCollided(paddle2, goal)) {
            if (goal.y < paddle2.y) {
                goal.x -= 10;
            } else if (goal.y < paddle2.y + paddle2.height) {
                goal.x -= 10;
            }
            goalSpeed.x = 2* -goalSpeed.x;
            if (random.nextInt(1,3) == 2) {
                goalSpeed.y = -speed;
            } else {
                goalSpeed.y = speed;
            }
        }

        if (goalSpeed.x > speed * 3) {
            goalSpeed.x = speed * 3;
        } else if (goalSpeed.x < - speed * 3) {
            goalSpeed.x = - speed * 3;
        }

        if (goalSpeed.y > speed * 3) {
            goalSpeed.y = speed * 3;
        } else if (goalSpeed.y < -speed * 3) {
            goalSpeed.y = -speed * 3;
        }
    }
}
