package org.goose.core.gui;

import org.goose.core.event.core.EventHandler;
import org.goose.core.event.core.EventListener;
import org.goose.core.event.core.EventManager;
import org.goose.core.event.events.core.RenderEndEvent;
import org.goose.core.gui.elements.Element;
import org.goose.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class GuiHandler implements EventListener {
    public static HashMap<Level, ArrayList<GuiHandler>> handlerList = new HashMap<>();
    private Level level;
    private TreeMap<Integer, ArrayList<Element>> elements = new TreeMap<>(); //sorted version of hashmap
    //will be used for zIndex

    public GuiHandler(Level level) {
        this.level = level;
        if (handlerList.get(level) == null) {
            ArrayList<GuiHandler> arrayList = new ArrayList<>();
            arrayList.add(this);
            handlerList.put(level, arrayList);
        } else {
            handlerList.get(level).add(this);
        }
        EventManager.addListener(this);
    }

    @EventHandler
    public void renderGuiElements(RenderEndEvent event) {
        if (level.isEnabled()) {
            //render
            for (Integer zIndex : elements.keySet()) {
                ArrayList<Element> elementsArrayList = elements.get(zIndex);
                for (Element element: elementsArrayList) {
                    element.render();
                }

            }
        }
    }

    public void removeElement(Element element) {
        Integer zIndex = element.getzIndex();
        if (elements.containsKey(zIndex)) {
            if (elements.get(zIndex) != null) {
                elements.get(zIndex).remove(element);
            }
        }
    }

    public void addElement(Element element) {
        Integer zIndex = element.getzIndex();
        if (elements.containsKey(zIndex)) {
            elements.get(zIndex).add(element);
        } else {
            ArrayList<Element> elementArrayList = new ArrayList<>();
            elementArrayList.add(element);
            elements.put(zIndex, elementArrayList);
        }
    }
}
