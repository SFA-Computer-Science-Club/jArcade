package org.goose.core.event.core;

import org.goose.core.event.events.Event;
import org.goose.core.event.events.core.TickEvent;
import org.goose.level.Level;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Main event class, listens for events and fires them to the listeners and their methods that are listening
 */
public class EventManager {

    /**
     * A list which contains the listeners of the event system
     */
    private static final HashMap<Class<Event>, CopyOnWriteArrayList<EventListener>> registeredListeners = new HashMap<>();

    @SuppressWarnings("unchecked")
    /**
     * Adds a listener to the registeredListeners list
     * @param listener
     */
    public static void addListener(EventListener listener) {
        for (final Method method : listener.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(EventHandler.class)) {
                Class<?>[] parameters = method.getParameterTypes();
                for (Class<?> type : parameters) {
                    if (type.getGenericSuperclass().equals(Event.class)) {
                        //the parameter includes a class which extends Event
                        if (!registeredListeners.containsKey(type)) {
                            //add a new arraylist of listeners
                            registeredListeners.put((Class<Event>) type, new CopyOnWriteArrayList<>());
                            registeredListeners.get(type).add(listener);
                        } else {
                            CopyOnWriteArrayList<EventListener> existingList = registeredListeners.get(type);
                            existingList.add(listener);
                        }
                    }
                }
            }
        }
    }

    public static void printAllListeners() {
        for (Class<Event> event : registeredListeners.keySet()) {
            System.out.println("Event: " + event.getSimpleName() + ", Listeners: " +  registeredListeners.get(event).toString());
        }
    }

    public static void deleteListener(EventListener listener) {
        for (final Method method : listener.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(EventHandler.class)) {
                Class<?>[] parameters = method.getParameterTypes();
                for (Class<?> type : parameters) {
                    if (type.getGenericSuperclass().equals(Event.class)) {
                        //the parameter includes a class which extends Event
                        if (registeredListeners.containsKey(type)) {
                            registeredListeners.get(type).remove(listener);
                        }
                    }
                }
            }
        }
    }

    public static void dispatchEvent(final Event event, final ListenerPriority priority) {
        //check if event queue for that event is empty
        if (!registeredListeners.containsKey(event.getClass())) {
            return; //tried to fire an event with no listeners
        }
        for (EventListener listener : registeredListeners.get(event.getClass())) {
            //iterates over the current listeners for that event
            if (listener instanceof Level) {
                if (!((Level) listener).isEnabled()) {
                    continue;
                }
            }
            for (final Method method : listener.getClass().getMethods()) {
                if (method.isAnnotationPresent(EventHandler.class)) {
                    EventHandler annotation = method.getAnnotation(EventHandler.class);
                    if (annotation.priority().ordinal() != priority.ordinal()) {
                        continue;
                    }
                    Class<?>[] parameters = method.getParameterTypes();
                    for (Class<?> type : parameters) {
                        if (type.getGenericSuperclass().equals(Event.class) && type.isInstance(event)) {
                            try {
                                method.invoke(listener, event);
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
}
