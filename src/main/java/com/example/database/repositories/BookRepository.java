package com.example.database.repositories;

import com.example.database.domain.entities.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends CrudRepository<Book, String>, PagingAndSortingRepository<Book, String> {
}
