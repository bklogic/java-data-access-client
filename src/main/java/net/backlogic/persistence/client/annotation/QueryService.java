package net.backlogic.persistence.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotate a Query interface with one or more @Query methods.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryService {
	String value() default "";
}
