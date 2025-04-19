package com.example.database.services;

import java.util.List;
import java.util.Optional;

import com.example.database.domain.entities.Author;

public interface AuthorService {
    Author createAuthor(Author author);

    List<Author> findAll();

    Optional<Author> findById(Long id);
}
