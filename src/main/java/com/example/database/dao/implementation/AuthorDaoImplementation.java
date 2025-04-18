package com.example.database.dao.implementation;

import com.example.database.dao.AuthorDao;
import com.example.database.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;

public class AuthorDaoImplementation implements AuthorDao {
    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoImplementation(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Author author) {
        jdbcTemplate.update("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)",
                author.getId(),
                author.getName(),
                author.getAge()
        );
    }
}
