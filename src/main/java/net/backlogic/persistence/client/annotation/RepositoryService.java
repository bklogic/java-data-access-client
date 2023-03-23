/**
 *
 */
package net.backlogic.persistence.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotate a Repository interface with one more @Read, @Create, @Update, @Delete, @Save, and @Merge methods, and map it a repository service.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RepositoryService {
    String value() default "";
}
