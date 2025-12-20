package org.example.application.Post;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcPostCategoryRepository implements PostCategoryRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcPostCategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(Long postId, Long categoryId) {
        jdbcTemplate.update(
                "INSERT INTO post_categories (post_id, category_id) VALUES (?,?)",
                postId, categoryId
        );
    }

    public List<Long> findCategoryIdsByPostId(Long postId) {
        return jdbcTemplate.queryForList(
                "SELECT category_id FROM post_categories WHERE post_id = ?",
                Long.class,
                postId
        );
    }

    public void delete(Long postId, Long categoryId) {
        jdbcTemplate.update(
                "DELETE FROM post_categories WHERE post_id = ? AND category_id = ?",
                postId, categoryId
        );
    }

    public void deleteByPostId(Long postId) {
        jdbcTemplate.update(
                "DELETE FROM post_categories WHERE post_id = ?",
                postId
        );
    }
}
