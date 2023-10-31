package net.backlogic.persistence.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>Annotate a query interface.</p>
 * <p>
 *     A query interface may include one or more command methods annotated with @Query.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryService {
	String value() default "";
}
