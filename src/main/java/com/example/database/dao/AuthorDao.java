package com.example.database.dao;

import com.example.database.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    void create(Author author);

    Optional<Author> findOne(long authorId);

    List<Author> find();
}
