package com.example.database.dao.implementation;

import com.example.database.TestDataUtil;
import com.example.database.domain.Author;
import com.example.database.domain.Book;
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
public class BookDaoImplementationTest {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImplementation underTest;

    @Test
    public void assertCreateBookGeneratesCorrectSql() {
        Book book = TestDataUtil.createTestBook();

        underTest.create(book);

        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
                eq("ISBN00123"),
                eq("My book"),
                eq(1L)
        );
    }

    @Test
    public void assertFindOneBookGeneratesCorrectSql() {
        underTest.findOne("ISBN00123");

        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
                ArgumentMatchers.<BookDaoImplementation.BookRowMapper>any(),
                eq("ISBN00123")
        );
    }

    @Test
    public void assertFindManyGeneratesCorrectSql() {
        underTest.find();

        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books"),
                ArgumentMatchers.<BookDaoImplementation.BookRowMapper>any()
        );
    }

    @Test
    public void assertUpdateGeneratesCorrectSql() {
        Book book = TestDataUtil.createTestBook();

        book.setIsbn("112233");
        book.setTitle("Abigail Rose");

        underTest.update("ISBN00123", book);

        verify(jdbcTemplate).update(
                eq("UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?"),
                eq("112233"),
                eq("Abigail Rose"),
                eq(1L),
                eq("ISBN00123")
        );
    }
}
