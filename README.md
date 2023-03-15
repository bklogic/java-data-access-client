# BackLogic Java Persistence Client
A Java client to work with BackLogic Persistence Platform to stremeline application persistecne programming, inspired by Spring Boot Data Repository patterns.



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

```java
@BatchService
public interface BatchInterface extends Batch, QueryInterface {
    
}
```

## Road Map

| Item | Phase 1 | Phase 2 | Phase 3 |
| --- | --- | --- |-------|
| basic | Y |
| batch interface | | Y |
| reactive |  | | Y      |

basic:
- command, query, repository
- synchonous
- 


