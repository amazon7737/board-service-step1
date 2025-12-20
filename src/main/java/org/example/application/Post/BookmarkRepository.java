package org.example.application.Post;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookmarkRepository {
    private final JdbcTemplate jdbcTemplate;


    public BookmarkRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Long userId, Long postId) {
        jdbcTemplate.update(
                "INSERT INTO bookmarks (user_id, post_id) VALUES (?, ?)",
                 userId, postId
        );
    }

    public boolean exists(Long userId, Long postId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM bookmarks WHERE user_id = ? AND post_id = ?",
                Integer.class,
                userId,
                postId
        );
        return count != null && count > 0;
    }

    public List<Long> findPostIdsByUserId(Long userId) {
        return jdbcTemplate.queryForList(
                "SELECT post_id FROM bookmarks WHERE user_id = ?",
                Long.class,
                userId
        );
    }

    public void delete(Long userId, Long postId) {
        jdbcTemplate.update(
                "DELETE FROM bookmarks WHERE user_id = ? AND post_id = ?",
                userId, postId
        );
    }

    public void deleteByPostId(Long postId) {
        jdbcTemplate.update(
                "DELETE FROM bookmarks WHERE post_id = ?",
                postId
        );
    }
}
