package org.example.application.Auth;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthorityRepository {
    private final JdbcTemplate jdbcTemplate;

    public AuthorityRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Authorities> mapper = (rs, rowNum) ->
            new Authorities(
                    rs.getLong("id"),
                    rs.getString("name")
            );

    public Optional<Authorities> findByUserId(Long userId) {
        return jdbcTemplate.query(
                "SELECT a.id, a.name FROM authorities a " +
                        "JOIN user_authorities ua ON a.id = ua.authority_id " +
                        "WHERE ua.user_id = ?",
                mapper,
                userId
        ).stream().findFirst();
    }
}
