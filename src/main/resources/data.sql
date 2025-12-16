-- users
INSERT INTO users (username, email) VALUES
                                        ('john', 'john@example.com'),
                                        ('alice', 'alice@example.com'),
                                        ('bob', 'bob@example.com'),
                                        ('charlie', 'charlie@example.com'),
                                        ('diana', 'diana@example.com');
INSERT INTO authorities (name) VALUES ('USER');
INSERT INTO authorities (name) VALUES ('ADMIN');

-- john : USER
INSERT INTO user_authorities (user_id, authority_id) VALUES
    (1, 1);

-- alice : USER + ADMIN
INSERT INTO user_authorities (user_id, authority_id) VALUES
                                                         (2, 1),
                                                         (2, 2);

-- bob : USER
INSERT INTO user_authorities (user_id, authority_id) VALUES
    (3, 1);

-- charlie : USER
INSERT INTO user_authorities (user_id, authority_id) VALUES
    (4, 1);

-- diana : USER
INSERT INTO user_authorities (user_id, authority_id) VALUES
    (5, 1);

-- categories
INSERT INTO categories (name) VALUES
                                  ('Spring'),
                                  ('Database'),
                                  ('Backend'),
                                  ('Architecture'),
                                  ('DevOps');

-- posts
INSERT INTO posts (user_id, title, content) VALUES
                                                (1, 'Spring JdbcTemplate 정리', 'JdbcTemplate 기본 사용법을 정리합니다.'),
                                                (2, 'JPA vs JDBC', 'JPA와 JDBC의 차이를 비교해봅니다.'),
                                                (3, '대댓글 구조 설계', '댓글과 대댓글을 어떻게 설계할까?'),
                                                (1, 'ERD 설계 팁', '게시판 ERD 설계시 주의점'),
                                                (4, '인덱스 최적화', 'DB 인덱스 튜닝 경험 공유'),
                                                (5, '북마크 기능 구현', '북마크 설계와 주의점');

-- post_categories
INSERT INTO post_categories (post_id, category_id) VALUES
                                                       (1, 1),
                                                       (1, 2),
                                                       (2, 2),
                                                       (2, 3),
                                                       (3, 3),
                                                       (3, 4),
                                                       (4, 4),
                                                       (5, 2),
                                                       (5, 5),
                                                       (6, 3);

-- comments (부모 댓글)
INSERT INTO comments (post_id, user_id, parent_id, content) VALUES
                                                                (1, 2, NULL, 'JdbcTemplate 설명이 깔끔하네요'),
                                                                (1, 3, NULL, '실무에서 많이 쓰이죠'),
                                                                (2, 1, NULL, '상황에 따라 선택해야 할 것 같아요'),
                                                                (3, 4, NULL, '대댓글 구조가 항상 고민됩니다');

-- comments (대댓글)
INSERT INTO comments (post_id, user_id, parent_id, content) VALUES
                                                                (1, 1, 1, '감사합니다! 다음엔 예제도 추가할게요'),
                                                                (1, 4, 2, '맞아요, 트랜잭션 제어가 중요하죠'),
                                                                (3, 3, 4, '트리 구조로 가는 게 좋더라구요');

-- bookmarks
INSERT INTO bookmarks (user_id, post_id) VALUES
                                             (1, 2),
                                             (1, 3),
                                             (2, 1),
                                             (3, 1),
                                             (3, 4),
                                             (5, 2);
