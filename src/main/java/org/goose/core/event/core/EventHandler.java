package org.goose.core.event.core;

import java.lang.annotation.*;
import org.goose.core.event.core.ListenerPriority;

/**
 * Eventhandler annotation, used to mark methods that are inside of a listener so they can be called upon an event
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventHandler {
    ListenerPriority priority() default ListenerPriority.NORMAL;
}
