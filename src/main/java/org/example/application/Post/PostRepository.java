package org.example.application.Post;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository {
    private final JdbcTemplate jdbcTemplate;


    public PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Post> mapper = (rs, rowNum) ->
            new Post(
                    rs.getLong("id"),
                    rs.getLong("user_id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getTimestamp("created_at").toLocalDateTime()
            );

    public long save(Long userId, String title, String content) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO posts (user_id, title, content) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setLong(1, userId);
            ps.setString(2, title);
            ps.setString(3, content);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Optional<Post> findById(Long id) {
        return jdbcTemplate.query(
                "SELECT * FROM posts WHERE id = ?",
                mapper,
                id
        ).stream().findFirst();
    }

    public List<Post> findAll(String sort) {
        String order = "DESC".equalsIgnoreCase(sort) ? "DESC" : "ASC";
        return jdbcTemplate.query(
                "SELECT * FROM posts ORDER BY created_at " + order,
                mapper
        );
    }

    public List<Post> findByUserId(Long userId) {
        return findByUserId(userId, "DESC");
    }

    public List<Post> findByUserId(Long userId, String sort) {
        String order = "DESC".equalsIgnoreCase(sort) ? "DESC" : "ASC";
        return jdbcTemplate.query(
                "SELECT * FROM posts WHERE user_id = ? ORDER BY created_at " + order,
                mapper,
                userId
        );
    }

    public void update(Long id, String title, String content) {
        jdbcTemplate.update(
                "UPDATE posts SET title = ?, content = ? WHERE id = ?",
                title, content, id
        );
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(
                "DELETE FROM posts WHERE id = ?",
                id
        );
    }
}
