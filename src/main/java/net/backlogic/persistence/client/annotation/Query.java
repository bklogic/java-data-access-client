package net.backlogic.persistence.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotate a query method and map it a query service.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {
	String value();
}
