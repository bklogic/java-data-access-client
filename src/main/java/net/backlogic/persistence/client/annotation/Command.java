package net.backlogic.persistence.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotate a command method and map it a command service.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
	String value();
}
