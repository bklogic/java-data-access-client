package net.backlogic.persistence.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotate a Batch interface.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BatchService {
    String value() default "";
}
