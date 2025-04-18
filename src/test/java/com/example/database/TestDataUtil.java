package com.example.database;

import com.example.database.domain.Author;
import com.example.database.domain.Book;

public final class TestDataUtil {
    private TestDataUtil() {}

    public static Author createTestAuthor() {
        return Author.builder()
                .name("Zohaib")
                .age(30)
                .build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .name("Zohaib 2")
                .age(30)
                .build();
    }

    public static Book createTestBook(final Author author) {
        return Book.builder()
                .isbn("ISBN00123")
                .title("My book")
                .author(author)
                .build();
    }

    public static Book createTestBookB(final Author author) {
        return Book.builder()
                .isbn("ISBN00124")
                .title("My book 2")
                .author(author)
                .build();
    }
}
