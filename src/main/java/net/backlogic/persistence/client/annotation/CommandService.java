package net.backlogic.persistence.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Annotate a command interface.</p>
 * <p>
 *     A command interface may include one or more command methods annotated with @Command.
 * </p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandService {
	String value() default "";
}
