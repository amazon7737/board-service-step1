# Code Review & Refactoring Learnings

This document summarizes the improvements made to the `board-service` during the code review process, focusing on SOLID principles, Design Patterns, and Effective Java practices.

## 1. SOLID Principles: Dependency Inversion Principle (DIP)

### Before
Services depended directly on concrete JDBC repositories (e.g., `PostRepository`).
```java
public class PostWriter {
    private final PostRepository postRepository; // Concrete class
}
```

### After
Services now depend on interfaces.
```java
public interface PostRepository { ... }

@Repository
public class JdbcPostRepository implements PostRepository { ... }

public class PostWriter {
    private final PostRepository postRepository; // Interface
}
```
**Learning**: By depending on abstractions rather than details, the code becomes more modular and easier to test (using mocks) and extend (e.g., switching from JDBC to JPA).

## 2. Design Patterns: Builder Pattern

### Learning
Using the Builder pattern (from *Effective Java*, Item 2) is preferable when a class has many fields, especially optional ones. It provides better readability and prevents "telescoping constructors."

```java
Post post = Post.builder()
    .userId(userId)
    .title(title)
    .content(content)
    .build();
```

## 3. Effective Java: Custom Exception Handling

### Learning
Throwing specific, domain-related exceptions is better than using generic `RuntimeException`. It makes the API clearer and allows for more granular error handling at the global level (e.g., with `@ControllerAdvice`).

- **BoardException**: Base runtime exception for the domain.
- **EntityNotFoundException**: Thrown when a specific resource is missing.
- **UnauthorizedAccessException**: Thrown for permission-related issues (Ownership validation).

## 4. Modern Java 8+ Features

### Learning
- **Optional**: Used `Optional.orElseThrow()` to handle potentially missing data in a functional style.
- **Streams**: Used `stream().findFirst()` when processing query results.
- **Java Time API**: Replaced `java.util.Date` with `java.time.LocalDateTime` for better thread safety and API clarity.

## 5. Ownership Validation (Security)
Implemented logic to verify that users can only modify/delete their own posts and comments. This is a critical business rule for any social platform.

```java
if (!post.getUserId().equals(userId)) {
    throw new UnauthorizedAccessException("...");
}
```
