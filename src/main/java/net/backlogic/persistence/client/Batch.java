package net.backlogic.persistence.client;

/**
 * <p>
 * The generic base class for batched services.
 * </p>
 * <p>
 * A batch service interface shall be annotated with {@literal @}BatchService
 * and extends this interface, which provides method to invoke the batched services.
 * The type T designates the return type of the batch service. It can be a DTO type
 * or a generic array.
 * </p>
 */
public interface Batch<T> {
	T run();
	T get();
	T save();
	void clean();
}
