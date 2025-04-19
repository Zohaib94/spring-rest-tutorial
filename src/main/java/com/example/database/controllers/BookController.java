package com.example.database.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.database.domain.dto.BookDto;
import com.example.database.domain.entities.Book;
import com.example.database.exceptions.BookNotFoundException;
import com.example.database.mappers.Mapper;
import com.example.database.services.BookService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class BookController {
  private Mapper<Book, BookDto> bookMapper;
  private BookService bookService;

  public BookController(BookService bookService, Mapper<Book, BookDto> bookMapper) {
    this.bookMapper = bookMapper;
    this.bookService = bookService;
  }

  @PutMapping("books/{isbn}")
  public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
      Book book = bookMapper.mapFrom(bookDto);
      Boolean isExistingBook = bookService.isExists(isbn);
      Book savedBook = bookService.createBook(isbn, book);

      HttpStatus returnStatus = isExistingBook ? HttpStatus.OK : HttpStatus.CREATED;

      return new ResponseEntity<>(bookMapper.mapTo(savedBook), returnStatus);
  }

    @GetMapping(path = "/books")
    public List<BookDto> listBooks() {
      List<Book> books = bookService.findAll();
      return books.stream().map(bookMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable String isbn) {
        Optional<Book> bookEntity = bookService.findByIsbn(isbn);

        return bookEntity.map(book -> {
          BookDto bookDto = bookMapper.mapTo(book);
          return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(@PathVariable String isbn, @RequestBody BookDto book) {
        Book bookEntity = bookMapper.mapFrom(book);
        try {
            Book savedBook = bookService.partialUpdate(isbn, bookEntity);
            BookDto saveBookDto = bookMapper.mapTo(savedBook);

            return new ResponseEntity<>(saveBookDto, HttpStatus.OK);
        } catch (BookNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
