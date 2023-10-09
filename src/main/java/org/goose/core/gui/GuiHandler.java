package org.goose.core.gui;

import org.goose.core.event.core.EventListener;
import org.goose.core.event.core.EventManager;
import org.goose.core.gui.elements.Element;
import org.goose.level.Level;

import java.util.ArrayList;
import java.util.HashMap;

public class GuiHandler {
    private static HashMap<Level, ArrayList<Element>> guiElementsList = new HashMap<>();

    public static boolean elementShouldBeRendered(Element element) {
        if (guiElementsList.containsKey(element.getLevel())) {
            if (guiElementsList.get(element.getLevel()) != null) {
                if (element.getLevel().isEnabled()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void printAll() {
        for (Level level : guiElementsList.keySet()) {
            System.out.println(guiElementsList.get(level));
        }
    }

    public static void removeElement(Element element, Level level) {
        if (guiElementsList.containsKey(level)) {
            if (guiElementsList.get(level) != null) {
                guiElementsList.get(level).remove(element);
                //also remove it from the event system
                EventManager.deleteListener(element);
            }
        }
    }

    public static void addElement(Element element, Level level) {
        if (guiElementsList.containsKey(level)) {
            //add to the arraylist if it exists
            if (guiElementsList.get(level) != null) {
                guiElementsList.get(level).add(element);
                EventManager.addListener(element);
                element.setLevel(level);
            } else {
                ArrayList<Element> list = new ArrayList<>();
                list.add(element);
                EventManager.addListener(element);
                element.setLevel(level);
                guiElementsList.put(level, list);
            }
        } else {
            ArrayList<Element> list = new ArrayList<>();
            list.add(element);
            element.setLevel(level);
            EventManager.addListener(element);
            guiElementsList.put(level, list);
        }
    }
}
