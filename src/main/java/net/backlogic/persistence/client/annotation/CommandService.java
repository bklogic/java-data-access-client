package net.backlogic.persistence.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotate a Command interface with one or more @Command methods.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandService {
	String value() default "";
}
