package org.goose.core.gui.elements;

import org.goose.level.Level;

import java.util.ArrayList;
import java.util.EventListener;
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

    public static void removeElement(Element element, Level level) {
        if (guiElementsList.containsKey(level)) {
            if (guiElementsList.get(level) != null) {
                guiElementsList.get(level).remove(element);
                //also remove it from the event system
                if (element instanceof EventListener) {

                }
            }
        }
    }

    /**
     * Render all elements
     */
    public static void renderElements() {
        for (Level level : guiElementsList.keySet()) {
            if (!level.isEnabled()) {
                continue;
            }
            for (Element element : guiElementsList.get(level)) {
                if (element.isVisible()) {
                    element.render(0);
                }
            }
        }
    }

    public static void addElement(Element element, Level level) {
        if (guiElementsList.containsKey(level)) {
            //add to the arraylist if it exists
            if (guiElementsList.get(level) != null) {

            } else {
                ArrayList<Element> list = new ArrayList<>();
                list.add(element);
                guiElementsList.put(level, list);
            }
        } else {
            ArrayList<Element> list = new ArrayList<>();
            list.add(element);
            guiElementsList.put(level, list);
        }
    }
}
