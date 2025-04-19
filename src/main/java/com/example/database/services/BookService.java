package com.example.database.services;

import java.util.List;
import java.util.Optional;

import com.example.database.domain.entities.Book;

public interface BookService {
  Book createBook(String isbn, Book book);
  List<Book> findAll();
  Optional<Book> findByIsbn(String isbn);
}
