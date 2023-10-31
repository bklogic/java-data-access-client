package net.backlogic.persistence.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>Annotate a command interface.</p>
 * <p>
 *     A command interface may include one or more command methods annotated with @Command.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandService {
	String value() default "";
}
