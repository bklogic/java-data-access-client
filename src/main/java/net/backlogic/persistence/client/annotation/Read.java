package net.backlogic.persistence.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotate a read method and map it to a read operation of a repository
 * service.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Read {
    String value() default "";
}
