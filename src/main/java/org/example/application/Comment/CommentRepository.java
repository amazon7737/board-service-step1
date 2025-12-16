package org.example.application.Comment;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepository {
    private final JdbcTemplate jdbcTemplate;

    public CommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Comment> mapper = (rs, rowNum) ->
            new Comment(
                    rs.getLong("id"),
                    rs.getLong("post_id"),
                    rs.getLong("user_id"),
                    rs.getObject("parent_id", Long.class),
                    rs.getString("content"),
                    rs.getTimestamp("created_at").toLocalDateTime()
            );

    public void save(Long postId, Long userId, Long parentId, String content) {
        jdbcTemplate.update(
                "INSERT INTO comments (post_id, user_id, parent_id, content) VALUES (?, ?,?,?)",
                postId, userId, parentId, content
        );
    }

    public List<Comment> findByPostId(Long postId) {
        return jdbcTemplate.query(
                "SELECT * FROM comments WHERE post_id = ? ORDER BY parent_id, created_at",
                mapper,
                postId
        );
    }

    public Optional<Comment>  findById(Long id) {
        return jdbcTemplate.query(
                "SELECT * FROM comments WHERE id = ?",
                mapper,
                id
        ).stream().findFirst();
    }

    public void updateContent(Long id, String content) {
        jdbcTemplate.update(
                "UPDATE comments SET content = ? WHERE id = ?",
                content, id
        );
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(
                "DELETE FROM comments WHERE id = ? OR parent_id = ?",
                id, id
        );
    }
}
