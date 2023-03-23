package net.backlogic.persistence.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotate a create method and map it to the create operation of a repository
 * service.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Create {
	String value() default "";
}
