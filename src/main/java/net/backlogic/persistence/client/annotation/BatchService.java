package net.backlogic.persistence.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotate a Batch interface with one more @Qqery, @Command, @Read, @Create, @Update, @Delete, @Save, and @Merge methods.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BatchService {
    String value() default "";
}
