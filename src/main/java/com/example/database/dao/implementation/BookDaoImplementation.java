package com.example.database.dao.implementation;

import com.example.database.dao.BookDao;
import com.example.database.domain.Author;
import com.example.database.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class BookDaoImplementation implements BookDao {
    private final JdbcTemplate jdbcTemplate;

    public BookDaoImplementation(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Book book) {
        jdbcTemplate.update("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)",
                book.getIsbn(),
                book.getTitle(),
                book.getAuthorId()
        );
    }

    @Override
    public Optional<Book> findOne(String isbn) {
        List<Book> results = jdbcTemplate.query(
                "SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1",
                new BookDaoImplementation.BookRowMapper(),
                isbn
        );

        return results.stream().findFirst();
    }

    @Override
    public List<Book> find() {
        List<Book> results = jdbcTemplate.query(
                "SELECT isbn, title, author_id FROM books",
                new BookDaoImplementation.BookRowMapper()
        );

        return results.stream().toList();
    }

    public void update(String isbn, Book book) {
        jdbcTemplate.update(
                "UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?",
                book.getIsbn(),
                book.getTitle(),
                book.getAuthorId(),
                isbn
        );
    }

    public static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Book.builder()
                    .isbn(rs.getString("isbn"))
                    .title(rs.getString("title"))
                    .authorId(rs.getLong("author_id"))
                    .build();
        }
    }
}
