# Java Data Access Client (JDAC)

A Java client for data access services. It follows the familiar data access object (DAO) and 
repository design patterns, except that here the user writes the annotated DAO and repository interface, 
but the data access client provides an implementation behind the scene and invokes 
the remote data access service when the interface method is called.  

If you don't know what data access service is, please take a look at:  

[Data Access Service Documentation](https://docs.backlogic.net/#/DataAccessService)  

It is a simple way to take care of complex relational database access problem.

To get started with this Java client, please read on.

## Get Started

### Maven Dependency

```xml
<dependency>
    <groupId>net.backlogic.persistence</groupId>
    <artifactId>jdac</artifactId>
    <version>0.1.6</version>
</dependency>
``` 

### Data Access Client

```goovy
DataAccessClient client = DataAccessClient.builder()
	.baseUrl("https://try4.devtime.tryprod.backlogic.net/service/try4/example")
	.build();
```

### Query Interface

```java
@QueryService("myQueries")
public interface MyQuery {
	@Query("getCustomers")
	public List<Customer> getCustomersByCity(String city);
}
```

### Command Interface

``` java
@CommandService("myCommands")
public interface MyCommand {
	@Command("removeCustomer")
	public void removeCustomer(Integer customerNumber);
}
```

### Repository Interface

``` java
@RepositoryService("myRepositories/Customer")
public interface CustomerRepository {
	@Create
	public Customer create(Customer customer);
	@Update
	public Customer update(Customer customer);
	@Delete
	public Customer delete(Customer customer);
	@Read
	public Customer getById(Integer customerNumber);
	@Read
	public List<Customer> getByCity(String city);
}
```

### Data Access Calls

```groovy
// query
MyQuery myQuery = client.getQuery(MyQuery.class);
List<Customer> customers = myQuery.getCustomersByCity("Los Angeles")

// command
MCommand myCommand = client.getCommand(MCommand.class);
myQuery.removeCustomer(123);

// repository
Customer customer = new Customer("Joe", "Los Angeles");
CustomerRepository repository = client.getRepository(CustomerRepository.class);
customer = repository.create(customer)
```

## Logging

Logging may be enabled for JDAC to log all service requests and responses as follows:

```groovy
DataAccessClient client = DataAccessClient.builder()
        .baseUrl("https://try4.devtime.tryprod.backlogic.net/service/try4/example")
        .logRequest(true)
        .build();
```

## JWT Based Authentication

A JWT bearer token will be sent with service request, if a JWT provider is configured as follows:

```groovy
JwtProvider jwtProvider = new SimpleJwtProvider().setJwt("myJwtString")
DataAccessClient client = DataAccessClient.builder()
        .baseUrl("https://try4.devtime.tryprod.backlogic.net/service/try4/example")
        .logRequest(true)
        .jwtProvider(jwtProvider)
        .build();
```

The JwtProvider is a simple interface with three methods:

```groovy
public interface JwtProvider {
    String get();
    void refresh();
    void set(Properties properties);
}
```

- get() for getting a JWT token
- refresh() for refreshing cached JWT token, so that the next get() returns a new JWT token
- set() for setting the properties of the JwtProvider

Besides the naive SimpleJwtProvider that simply takes and stores a JWT token, JDAC also implements
a BasicJwtProvider that relies on an auth service for JWT token:

```groovy
BasicJwtProviderProperties properties = new BasicJwtProviderProperties(authEndpoint, serviceKey, serviceSecrete);
JwtProvider jwtProvider = new BasicJwtProvider(properties);
DataAccessClient client = DataAccessClient.builder()
        .baseUrl("https://try4.devtime.tryprod.backlogic.net/service/try4/example")
        .logRequest(true)
        .jwtProvider(jwtProvider)
        .build();
```

For DevTime service hosted in BackLogic workspace, authEndpoint, serviceKey, serviceSecrete are available 
from Service Builder, the VSCode extension for data access service development.

## Spring Boot

For Spring Boot users, a Spring Boot Starter is available here:  

[JDAC Spring Boot Starter](https://github.com/bklogic/jdac-spring-boot-starter)

It embeds JDAC inside of the starter, and automatically registers all custom data access interfaces as Spring Boot beans. 
Thus, the user may simply `Autowire` a query, command or repository interface and start to call its methods. 
It is even easier and cleaner than dealing with the DataAccessClient directly. 



