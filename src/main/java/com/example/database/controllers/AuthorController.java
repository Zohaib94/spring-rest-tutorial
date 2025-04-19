package com.example.database.controllers;

import com.example.database.domain.dto.AuthorDto;
import com.example.database.domain.entities.Author;
import com.example.database.exceptions.AuthorNotFoundException;
import com.example.database.mappers.Mapper;
import com.example.database.services.AuthorService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class AuthorController {
    private AuthorService authorService;
    private Mapper<Author, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<Author, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
        Author authorEntity = authorMapper.mapFrom(author);
        Author savedAuthor = authorService.saveAuthor(authorEntity);

        return new ResponseEntity<>(authorMapper.mapTo(savedAuthor), HttpStatus.CREATED);
    }

    @GetMapping(path = "/authors")
    public List<AuthorDto> listAuthors() {
        List<Author> authors = authorService.findAll();
        return authors.stream().map(authorMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable Long id) {
        Optional<Author> authorEntity = authorService.findById(id);

        return authorEntity.map(author -> {
            AuthorDto authorDto = authorMapper.mapTo(author);
            return new ResponseEntity<>(authorDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Long id, @RequestBody AuthorDto author) {
        Author authorEntity = authorMapper.mapFrom(author);
        
        try {
            Author savedAuthor = authorService.updateAuthor(id, authorEntity);
            AuthorDto saveAuthorDto = authorMapper.mapTo(savedAuthor);

            return new ResponseEntity<>(saveAuthorDto, HttpStatus.OK);
        } catch (AuthorNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdateAuthor(@PathVariable Long id, @RequestBody AuthorDto author) {
        Author authorEntity = authorMapper.mapFrom(author);
        try {
            Author savedAuthor = authorService.partialUpdate(id, authorEntity);
            AuthorDto saveAuthorDto = authorMapper.mapTo(savedAuthor);

            return new ResponseEntity<>(saveAuthorDto, HttpStatus.OK);
        } catch (AuthorNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
