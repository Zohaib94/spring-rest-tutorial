package com.example.database.services.implementation;

import com.example.database.config.RabbitMQConfig;
import com.example.database.domain.entities.Book;
import com.example.database.exceptions.BookNotFoundException;
import com.example.database.repositories.BookRepository;
import com.example.database.services.BookService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImplementation implements BookService {
    private BookRepository bookRepository;
    private RabbitTemplate rabbitTemplate;
    public BookServiceImplementation(BookRepository bookRepository, RabbitTemplate rabbitTemplate) {
        this.bookRepository = bookRepository;
        this.rabbitTemplate = rabbitTemplate;
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

    @Override
    public Boolean isExists(String isbn) {
      return bookRepository.existsById(isbn);
    }

    @Override
    public Book partialUpdate(String isbn, Book bookEntity) throws BookNotFoundException {
        bookEntity.setIsbn(isbn);

        return bookRepository.findById(isbn).map(existingBook -> {
            Optional.ofNullable(bookEntity.getTitle()).ifPresent(existingBook::setTitle);
            return bookRepository.save(existingBook);
        }).orElseThrow(() -> new BookNotFoundException("Book does not exist in database"));
    }

    @Override
    public void deleteBook(String isbn) {
      bookRepository.deleteById(isbn);
    }

    @Override
    public Page<Book> findAll(Pageable pageable) throws Exception {
      Page<Book> books = bookRepository.findAll(pageable);

       books.getContent().forEach(book -> {
         rabbitTemplate.convertAndSend(
             RabbitMQConfig.EXCHANGE_NAME,
             RabbitMQConfig.ROUTING_KEY,
             book
         );
       });

      return books;
    }
}
