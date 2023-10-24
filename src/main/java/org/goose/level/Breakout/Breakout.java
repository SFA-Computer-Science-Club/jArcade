package org.goose.level.Breakout;



import java.security.PublicKey;

import org.goose.core.Physics;
import org.goose.core.Renderer;
import org.goose.core.event.core.EventHandler;
import org.goose.core.event.core.ListenerPriority;
import org.goose.core.event.events.core.RenderDrawEvent;
import org.goose.core.event.events.core.TickEvent;
import org.goose.level.Level;

import com.raylib.java.core.Color;
import com.raylib.java.models.vox;
import com.raylib.java.shapes.Rectangle;
import com.raylib.java.shapes.rShapes;

public class Breakout extends Level {
    Rectangle paddle = new Rectangle(605, 700, 200, 20);
    Rectangle goal = new Rectangle(Renderer.getWindowWidth()/2f, 500, 25,25);
    Rectangle block = new Rectangle(50, 50, 80, 30);
    Rectangle leftBorder = new Rectangle(0, 0, 10, Renderer.getWindowHeight());
    Rectangle rightBorder = new Rectangle(1357, 0, 10, Renderer.getWindowHeight());
    Rectangle topBorder = new Rectangle(0, 18, Renderer.getWindowWidth(), 10);
    
    public Breakout() {
      this.setEnabled(true);  
    }

    
    
    @EventHandler
    public void Color(RenderDrawEvent event) {
        rShapes.DrawRectangleRec(paddle, Color.RED);
        rShapes.DrawRectangleRec(goal, Color.WHITE);
        rShapes.DrawRectangleRec(block, Color.BLUE);
        rShapes.DrawRectangleRec(rightBorder, Color.GREEN);
        rShapes.DrawRectangleRec(leftBorder, Color.GREEN);
        rShapes.DrawRectangleRec(topBorder, Color.GREEN);
    }

    
    @EventHandler
    public void tick(TickEvent event){
        if(Physics.rectCollided(goal, paddle)){

        }

        
    }



}

