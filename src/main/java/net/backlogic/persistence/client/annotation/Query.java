package net.backlogic.persistence.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotate a Query interface or a Query method of a DAO interface. Value is a partial service url.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {
	String value();
}
