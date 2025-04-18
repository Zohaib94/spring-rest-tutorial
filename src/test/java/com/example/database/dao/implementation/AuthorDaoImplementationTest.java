package com.example.database.dao.implementation;

import com.example.database.TestDataUtil;
import com.example.database.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplementationTest {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImplementation underTest;

    @Test
    public void assertCreateAuthorGeneratesCorrectSql() {
        Author author = TestDataUtil.createTestAuthor();

        underTest.create(author);

        verify(jdbcTemplate).update(
                eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
                eq(1L),
                eq("Zohaib"),
                eq(30)
        );
    }

    @Test
    public void assertFindOneGeneratesCorrectSql() {
        underTest.findOne(1L);

        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"),
                ArgumentMatchers.<AuthorDaoImplementation.AuthorRowMapper>any(),
                eq(1L)
        );
    }

    @Test
    public void assertFindManyGeneratesCorrectSql() {
        underTest.find();

        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors"),
                ArgumentMatchers.<AuthorDaoImplementation.AuthorRowMapper>any()
        );
    }
}
