package net.backlogic.persistence.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotate a Query interface. Value is a partial service url.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryService {
    String value() default "";
}
