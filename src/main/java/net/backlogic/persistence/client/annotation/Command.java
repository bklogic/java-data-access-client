package net.backlogic.persistence.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Method annotation for Query and Command interface. A mechanism to code service url.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String value();
}
