package net.backlogic.persistence.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotate a Create method of a Repository interface.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Create {
    String value() default "";
}
