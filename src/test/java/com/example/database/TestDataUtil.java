package com.example.database;

import com.example.database.domain.Author;
import com.example.database.domain.Book;

public final class TestDataUtil {
    private TestDataUtil() {}

    public static Author createTestAuthor() {
        return Author.builder()
                .id(1L)
                .name("Zohaib")
                .age(30)
                .build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .id(2L)
                .name("Zohaib 2")
                .age(30)
                .build();
    }

    public static Book createTestBook() {
        return Book.builder()
                .isbn("ISBN00123")
                .title("My book")
                .authorId(1L)
                .build();
    }

    public static Book createTestBookB() {
        return Book.builder()
                .isbn("ISBN00124")
                .title("My book 2")
                .authorId(1L)
                .build();
    }
}
