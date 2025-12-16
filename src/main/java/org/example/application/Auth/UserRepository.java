package org.example.application.Auth;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> mapper = (rs, rowNum) ->
            new User(
            rs.getLong("id"),
            rs.getString("password"),
            rs.getString("username"),
            rs.getString("email"),
            rs.getTimestamp("created_at").toLocalDateTime()
    );

    public void save(String username, String email, String password) {
        jdbcTemplate.update(
                "INSERT INTO users (username, email, password) VALUES (?, ?, ?)",
                username, email, password
        );
    }

    public User findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id = ?",
                mapper,
                id
        );
    }

    public Optional<User> findByEmail(String email) {
        return jdbcTemplate.query(
                "SELECT * FROM users WHERE email = ?",
                mapper,
                email
        ).stream().findFirst();
    }

    public Optional<User> findByIdAndPassword(Long id, String pw) {
        return jdbcTemplate.query(
                "SELECT * FROM users WHERE id = ? and password = ?",
                mapper,
                id, pw
        ).stream().findFirst();
    }

    public List<User> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM users",
                mapper
        );
    }

    public void updateUsername(Long id, String username) {
        jdbcTemplate.update(
                "UPDATE users SET username = ? WHERE id = ?",
                username, id
        );
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(
                "DELETE FROM users WHERE id = ?",
                id
        );
    }
}
