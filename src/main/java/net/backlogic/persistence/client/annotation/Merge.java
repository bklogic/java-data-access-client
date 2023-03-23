package net.backlogic.persistence.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotate a merge method and map it to the merge operation of a repository
 * service.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Merge {
    String value() default "";
}
