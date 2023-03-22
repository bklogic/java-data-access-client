# Java Data Access Client

A Java client for data access services. It follows the familiar data access object (DAO) and repository design patterns, except here the user writes the annotated DAO and repository interface, but the data access client provides an implementation behind the scene and invokes the remote data access service when any of the interface methods is called.  

To understand data access services, please take a look at:
[Data Access Service Documentation](https://docs.backlogic.net/#/DataAccessService)

To get started with this Java client, please read on.

## Get Started

### Maven Dependency

```xml
	<dependency>
		  <groupId>net.backlogic.persistence</groupId>
		  <artifactId>data-access-client</artifactId>
		  <version>0.0.1</version>
	</dependency>
``` 

### Get Data Access Client

```json
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

```java
// query
MyQuery myQuery = client.getQuery(MyQuery.class);
List<Customer> customers = myQuery.getCustomersByCity("Los Angeles")

// command
MCommand myCommand = client.getCommand(MCommand.class);
myQuery.removeCustomer(123);

// repository
Customer customer = new Customer();
CustomerRepository repository = client.getRepository(CustomerRepository.class);
customer = repository.create(customer)

```

## User Guide

For more details, please read:
[Java Data Access Client User Guide]()

## Spring Boot

For Spring Boot users, we have a Spring Boot starter here:
[Data Access Spring Boot Starter](https://github.com/bklogic/backlogic-spring-boot-starter)


