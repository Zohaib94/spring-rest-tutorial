package com.example.database.services;

import java.util.List;
import java.util.Optional;

import com.example.database.domain.entities.Author;
import com.example.database.exceptions.AuthorNotFoundException;

public interface AuthorService {
    Author saveAuthor(Author author);
    List<Author> findAll();
    Optional<Author> findById(Long id);
    Author updateAuthor(Long id, Author author) throws AuthorNotFoundException;
    Author partialUpdate(Long id, Author author);
    void deleteAuthor(Long id);
}
