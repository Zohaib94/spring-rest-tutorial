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

    public static Book createTestBook() {
        return Book.builder()
                .isbn("ISBN00123")
                .title("My book")
                .authorId(1L)
                .build();
    }
}
