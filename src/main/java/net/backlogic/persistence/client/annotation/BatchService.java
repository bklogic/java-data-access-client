package net.backlogic.persistence.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Annotate a Batch interface.</p>
 * <p>
 * A batch interface defines a set of batched data access services that are to be invoked as a batch.
 * Each annotated method in the batch interface define a data access service to be included in tha batch.
 * Services included in a batch are performed as a single transaction.
 * </p>
 * <p>
 * The batch interface must extends the Batch interface, which includes methods to invoke the batch service.
 * Currently supported batch services include batched queries, batched commands and batched crud operations.
 * </p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BatchService {
    String value() default "";
}
