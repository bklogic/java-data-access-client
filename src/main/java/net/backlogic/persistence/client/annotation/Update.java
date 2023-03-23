package net.backlogic.persistence.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotate an update method and map it to the update operation of a repository
 * service.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Update {
	String value() default "";
}
