package com.example.database.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.database.domain.entities.Book;

public interface BookService {
  Book createBook(String isbn, Book book);
  List<Book> findAll();
  Optional<Book> findByIsbn(String isbn);
  Boolean isExists(String isbn);
  Book partialUpdate(String isbn, Book bookEntity);
  void deleteBook(String isbn);
  Page<Book> findAll(Pageable pageable);
}
