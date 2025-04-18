package com.example.database.dao.implementation;

import com.example.database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplementationTest {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImplementation underTest;

    @Test
    public void assertCreateBookGeneratesCorrectSql() {
        Book book = Book.builder()
                .isbn("ISBN00123")
                .title("My book")
                .authorId(1L)
                .build();

        underTest.create(book);

        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
                eq("ISBN00123"),
                eq("My book"),
                eq(1L)
        );
    }
}
