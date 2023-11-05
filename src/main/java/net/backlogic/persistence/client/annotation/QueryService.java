package net.backlogic.persistence.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Annotate a query interface.</p>
 * <p>
 *     A query interface may include one or more command methods annotated with @Query.
 * </p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryService {
	String value() default "";
}
