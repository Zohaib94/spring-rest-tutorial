package com.example.database.services.implementation;

import com.example.database.domain.entities.Author;
import com.example.database.repositories.AuthorRepository;
import com.example.database.services.AuthorService;

import java.util.List;
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
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public List<Author> findAll() {
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
