/**
 *
 */
package net.backlogic.persistence.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotate a Repository interface with one more @Read, @Create, @Update, @Delete, @Save, and @Merge methods, and map it a repository service.
 */
/**
 * <p>Annotate a repository interface and map it a CRUD service.</p>
 * <p>
 *     A repository may include as many as needed read methods annotated with @Read; but one method
 *     for each of CRUD write operations, annotated with @Create, @Update, @Delete, @Save, and @Merge, respectively.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RepositoryService {
    String value() default "";
}
