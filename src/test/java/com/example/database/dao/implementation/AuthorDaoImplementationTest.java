package com.example.database.dao.implementation;

import com.example.database.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
        Author author = Author.builder()
                .id(1L)
                .name("Zohaib")
                .age(30)
                .build();

        underTest.create(author);

        verify(jdbcTemplate).update(
                eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
                eq(1L),
                eq("Zohaib"),
                eq(30)
        );
    }
}
