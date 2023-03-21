# BackLogic Java Persistence Client
A Java client to work with BackLogic Persistence Platform to streamline application persistence programming, inspired by Spring Boot Data Repository patterns.



## Requirements:

- Jackson for JSON
- Spring WebClient for HTTP
- Support for synchronous and reactive interface
- Type of Interfaces: query, command, repository, and batch

## Annotations

### Query Service

```java
@QueryService
public interface QueryInterface<O, I...> {
    @Query
    public O queryMethod(I input ...);
}
```

### Command

```java
@CommandService
public interface CommandInterface<O, I...> {
    @Command
    public O commandMethod(I input, ...);
}
```

O could be void.

### Repository Service

```java
@RepositoryService
public interface RepositoryInterface<O, I> {
    
    @Read
    public O readMethod(I input);

    @Read
    public List<O> readMethod(I input);
    
    @Create
    public O create(O o);

    @Update
    public O update(O o);

    @Save
    public O save(O o);

    @Merge
    public O merge(O o);

    @Delete
    public O delete(O o);

    @Delete
    public O deleteByKey(K k);

}
```

### Batch Interface

Example:

```java
@RepositoryInterface("/repository/customer")
public interface CustomerRepository {
	@Create
	Customer create(Customer customer)
}

@RepositoryInterface("/repository/order")
public interface OrderRepository {
	@Create
	Order create(Order order)
}


@BatchService("repository")
public interface MyBatch extends Batch {

	@BatchItem("customer/create")
	public void createCustomer(Customer customer)

	@BatchItem("order/create")
	public void createOrder(Order order)
}
```

```java
DataAccessClient client = new DataAccessClient(baseUrl);
MyBatch myBatch = client.getBatch(MyBatch.class);

myBatch.createCustomer(customer);
myBatch.createOrder(order);
Object[] output = myBatch.run()
Customer customer = (Cusomer) output[0]
Order order = (Order) output[1]
```


## Road Map

| Item | Phase 1 | Phase 2 | Phase 3 |
| --- | --- | --- |-------|
| basic | Y |
| batch interface | | Y |
| reactive |  | | Y      |

basic:
- command, query, repository
- synchronous
- 


