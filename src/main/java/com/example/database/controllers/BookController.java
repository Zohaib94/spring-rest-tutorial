package com.example.database.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.database.domain.dto.BookDto;
import com.example.database.domain.entities.Book;
import com.example.database.mappers.Mapper;
import com.example.database.services.BookService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
      Book savedBook = bookService.createBook(isbn, book);

      return new ResponseEntity<>(bookMapper.mapTo(savedBook), HttpStatus.CREATED);
  }

    @GetMapping(path = "/books")
    public List<BookDto> listBooks() {
      List<Book> books = bookService.findAll();
      return books.stream().map(bookMapper::mapTo).collect(Collectors.toList());
    }
}
