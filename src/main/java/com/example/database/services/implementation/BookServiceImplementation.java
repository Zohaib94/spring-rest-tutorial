package com.example.database.services.implementation;

import com.example.database.domain.entities.Book;
import com.example.database.repositories.BookRepository;
import com.example.database.services.BookService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

@Service
public class BookServiceImplementation implements BookService {
    private BookRepository bookRepository;

    public BookServiceImplementation(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book createBook(String isbn, Book book) {
      book.setIsbn(isbn);  
      return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
      return StreamSupport.stream(bookRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
      return bookRepository.findById(isbn);
    }
}
