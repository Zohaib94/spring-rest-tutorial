package com.example.database.services.implementation;

import com.example.database.domain.entities.Author;
import com.example.database.exceptions.AuthorNotFoundException;
import com.example.database.repositories.AuthorRepository;
import com.example.database.services.AuthorService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImplementation implements AuthorService {
    private AuthorRepository authorRepository;

    public AuthorServiceImplementation(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public List<Author> findAll() {
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Author updateAuthor(Long id, Author author) throws AuthorNotFoundException {
        Optional<Author> authorEntity = findById(id);

        if (authorEntity.isEmpty()) {
            throw new AuthorNotFoundException("Author does not exist in database");
        }

        author.setId(id);
        return saveAuthor(author);
    }
}
