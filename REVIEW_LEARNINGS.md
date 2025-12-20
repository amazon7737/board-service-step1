# 코드 리뷰 및 리팩토링 학습 기록

이 문서는 코드 리뷰 과정을 통해 `board-service`에 적용된 개선 사항들을 정리합니다. 주요 초점은 SOLID 원칙, 디자인 패턴, 그리고 Effective Java 실행 관례에 맞췄습니다.

## 1. SOLID 원칙: 의존성 역전 원칙 (DIP)

### 적용 전
서비스 계층이 구체적인 JDBC 저장소 클래스(예: `PostRepository`)에 직접 의존했습니다.
```java
public class PostWriter {
    private final PostRepository postRepository; // 구체 클래스
}
```

### 적용 후
이제 서비스는 인터페이스에 의존합니다.
```java
public interface PostRepository { ... }

@Repository
public class JdbcPostRepository implements PostRepository { ... }

public class PostWriter {
    private final PostRepository postRepository; // 인터페이스
}
```
**배운 점**: 상세 구현이 아닌 추상화에 의존함으로써 코드가 더 모듈화되었습니다. 이는 테스트(Mock 사용)가 쉬워지고, 향후 JDBC에서 JPA로 교체하는 등 확장이 용이해지는 결과로 이어집니다.

## 2. 디자인 패턴: 빌더(Builder) 패턴

### 배운 점
필드가 많거나 선택적 필드가 있는 클래스의 경우, 빌더 패턴(*Effective Java*, 아이템 2)을 사용하는 것이 좋습니다. 이는 가독성을 높이고 "점증적 생성자(Telescoping Constructor)" 문제를 방지합니다.

```java
Post post = Post.builder()
    .userId(userId)
    .title(title)
    .content(content)
    .build();
```

## 3. Effective Java: 커스텀 예외 처리

### 배운 점
일반적인 `RuntimeException`을 던지는 것보다 도메인과 관련된 구체적인 예외를 던지는 것이 좋습니다. 이는 API를 더 명확하게 만들며, 전역 수준(예: `@ControllerAdvice`)에서 더 세밀한 에러 핸들링을 가능하게 합니다.

- **BoardException**: 도메인 전체를 아우르는 최상위 실행 예외.
- **EntityNotFoundException**: 특정 자원을 찾을 수 없을 때 발생.
- **UnauthorizedAccessException**: 권한 관련 문제(소유권 검증 실패 등) 발생 시 사용.

## 4. 현대적인 Java 8+ 기능

### 배운 점
- **Optional**: 함수형 스타일로 데이터 부재 상황을 처리하기 위해 `Optional.orElseThrow()`를 활용했습니다.
- **Streams**: 쿼리 결과를 처리할 때 `stream().findFirst()` 등을 사용했습니다.
- **Java Time API**: 스레드 안전성과 API 명확성을 위해 `java.util.Date` 대신 `java.time.LocalDateTime`을 적용했습니다.

## 5. 소유권 검증 (보안)
사용자가 오직 자신이 작성한 게시글과 댓글만 수정/삭제할 수 있도록 검증 로직을 구현했습니다. 이는 소셜 플랫폼에서 매우 중요한 비즈니스 규칙입니다.

```java
if (!post.getUserId().equals(userId)) {
    throw new UnauthorizedAccessException("...");
}
```
