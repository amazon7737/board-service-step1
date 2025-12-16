DROP TABLE IF EXISTS post_categories;
DROP TABLE IF EXISTS bookmarks;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS user_authorities;

CREATE TABLE `users` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) UNIQUE NOT NULL,
  `created_at` datetime DEFAULT (now())
);

CREATE TABLE `authorities` (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE  `user_authorities` (
                                  user_id BIGINT NOT NULL,
                                  authority_id BIGINT NOT NULL,

                                  PRIMARY KEY (user_id, authority_id),

                                  CONSTRAINT fk_user_authorities_user
                                      FOREIGN KEY (user_id)
                                          REFERENCES users(id)
                                          ON DELETE CASCADE,

                                  CONSTRAINT fk_user_authorities_authority
                                      FOREIGN KEY (authority_id)
                                          REFERENCES authorities(id)
                                          ON DELETE CASCADE
);


CREATE TABLE `posts` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `created_at` datetime DEFAULT (now())
);



CREATE TABLE `comments` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `post_id` int NOT NULL,
  `user_id` int NOT NULL,
  `parent_id` int,
  `content` text NOT NULL,
  `created_at` datetime DEFAULT (now())
);

CREATE TABLE `bookmarks` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `post_id` int NOT NULL,
  `created_at` datetime DEFAULT (now())
);

CREATE TABLE `categories` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) UNIQUE NOT NULL,
  `created_at` datetime DEFAULT (now())
);

CREATE TABLE `post_categories` (
  `post_id` int NOT NULL,
  `category_id` int NOT NULL
);

CREATE UNIQUE INDEX `post_categories_index_0` ON `post_categories` (`post_id`, `category_id`);

ALTER TABLE `bookmarks` COMMENT = '북마크는 유저-게시글 사이의 다대다 관계를 나타내는 조인 테이블 역할';

ALTER TABLE `post_categories` COMMENT = '게시글 - 카테고리 다대다 관계용 조인 테이블';

ALTER TABLE `posts` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `comments` ADD FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`);

ALTER TABLE `comments` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `comments` ADD FOREIGN KEY (`parent_id`) REFERENCES `comments` (`id`);

ALTER TABLE `bookmarks` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `bookmarks` ADD FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`);

ALTER TABLE `post_categories` ADD FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`);

ALTER TABLE `post_categories` ADD FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);
