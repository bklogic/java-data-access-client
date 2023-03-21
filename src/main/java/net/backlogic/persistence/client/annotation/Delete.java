package net.backlogic.persistence.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotate a Delete method of a Repository interface.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Delete {
    String value() default "";
}
