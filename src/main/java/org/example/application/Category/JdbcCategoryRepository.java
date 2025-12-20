package org.example.application.Category;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcCategoryRepository implements CategoryRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcCategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Category> mapper = (rs, rowNum) ->
            new Category(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getTimestamp("created_at").toLocalDateTime()
            );

    public void save(String name) {
        jdbcTemplate.update(
                "INSERT INTO categories (name) VALUES (?)",
                name
        );
    }

    public Optional<Category> findById(Long id) {
        return jdbcTemplate.query(
                "SELECT * FROM categories WHERE id = ?",
                    mapper,
                id
        ).stream().findFirst();
    }

    public Optional<Category> findByName(String name) {
        return jdbcTemplate.query(
                "SELECT * FROM categories WHERE name = ?",
                mapper,
                name
        ).stream().findFirst();
    }

    public List<Category> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM categories",
                mapper
        );
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(
                "DELETE FROM categories WHERE id = ?",
                id
        );
    }
}
