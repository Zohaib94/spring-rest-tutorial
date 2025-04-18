package com.example.database.dao.implementation;

import com.example.database.dao.BookDao;
import com.example.database.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;

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
}
