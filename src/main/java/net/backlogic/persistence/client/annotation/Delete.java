package net.backlogic.persistence.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotate a delete method and map it to the DELETE operation of a CRUD service.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Delete {
    String value() default "";
}
