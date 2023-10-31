package net.backlogic.persistence.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotate a save method and map it to the SAVE operation of a CRUD service.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Save {
	String value() default "";
}
