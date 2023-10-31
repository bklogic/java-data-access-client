# Java Data Access Client

A Java client for data access services. It follows the familiar data access object (DAO) and repository design patterns, except here the user writes the annotated DAO and repository interface, but the data access client provides an implementation behind the scene and invokes the remote data access service when any of the interface methods is called.  

To understand data access services, please take a look :  
[Data Access Service Documentation](https://docs.backlogic.net/#/DataAccessService)  
It is a simple way to to solve your complex relational database access problem.

To get started with this Java client, please read on.

## Get Started

### Maven Dependency

```xml
<dependency>
    <groupId>net.backlogic.persistence</groupId>
    <artifactId>data-access-client</artifactId>
    <version>0.0.5</version>
</dependency>
``` 

### Get Data Access Client

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

Logging may be enabled for data access client to log all service requests and responses as follows:

```groovy
DataAccessClient client = DataAccessClient.builder()
        .baseUrl("https://try4.devtime.tryprod.backlogic.net/service/try4/example")
        .logRequest(true)
        .build();
```

## JWT Based Authentication

Data access client will send a bearer token with service request, if a JWT provider is configured as follows:

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
- refresh() for refreshing JWT cache, so that the next get() returns a new JWT token
- set() for setting the properties of the JwtProvider

Besides the naive SimpleJwtProvider that simply takes and stores a JWT token, data access client also implements
a BasicJwtProvider that relies an auth service for JWT token, to support the BackLogic DevTime service:

```groovy
BasicJwtProviderProperties properties = new BasicJwtProviderProperties(authEndpoint, serviceKey, serviceSecrete);
JwtProvider jwtProvider = new BasicJwtProvider().setJwt("myJwtString")
DataAccessClient client = DataAccessClient.builder()
        .baseUrl("https://try4.devtime.tryprod.backlogic.net/service/try4/example")
        .logRequest(true)
        .jwtProvider(jwtProvider)
        .build();
```

The authEndpoint, serviceKey, serviceSecrete for the DevTime service are available from the Service Builder `Show Workplace` menu.

## Spring Boot

For Spring Boot users, the BackLogic Data access Spring Boot Starter shall be used:  
[Data Access Spring Boot Starter](https://github.com/bklogic/backlogic-spring-boot-starter)

This starter automatically registers all data access interfaces as injectable Spring Boot beans, 
and the user can simply `Autowire` a query, command or repository interface and 
start to call its methods. 
