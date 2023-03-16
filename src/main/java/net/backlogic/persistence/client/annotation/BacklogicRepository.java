/**
 *
 */
package net.backlogic.persistence.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotate a Repository interface. Url should points to a CRUD service. Type indicates type of the aggregate root object.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BacklogicRepository {
    String value() default "";

    String url() default "";

    String type() default "";
}
