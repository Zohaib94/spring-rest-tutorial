package com.example.database.repositories;

import com.example.database.TestDataUtil;
import com.example.database.config.TestContainersConfig;
import com.example.database.domain.entities.Author;
import com.example.database.domain.entities.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTest extends TestContainersConfig {
    private BookRepository underTest;

    @Autowired
    public BookRepositoryIntegrationTest(BookRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void assertBookCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthor();

        Book book = TestDataUtil.createTestBook(author);
        book = underTest.save(book);

        Optional<Book> result = underTest.findById(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void assertMultipleBooksAreCreatedAndLoaded() {
        Author author = TestDataUtil.createTestAuthor();

        Book bookA = TestDataUtil.createTestBook(author);
        Book bookB = TestDataUtil.createTestBookB(author);

        bookA = underTest.save(bookA);
        bookB = underTest.save(bookB);

        Iterable<Book> bookList = underTest.findAll();

        Assertions.assertThat(bookList).hasSize(2);
        Assertions.assertThat(bookList).containsExactly(bookA, bookB);
    }

    @Test
    public void assertBookIsUpdated() {
        Author author = TestDataUtil.createTestAuthor();
        Book bookA = TestDataUtil.createTestBook(author);

        bookA = underTest.save(bookA);
        bookA.setTitle("Updated");
        bookA = underTest.save(bookA);

        Optional<Book> result = underTest.findById(bookA.getIsbn());

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(bookA);
    }

    @Test
    public void assertBookIsDeleted() {
        Author author = TestDataUtil.createTestAuthor();
        Book bookA = TestDataUtil.createTestBook(author);

        underTest.save(bookA);
        underTest.deleteById(bookA.getIsbn());

        Optional<Book> result = underTest.findById(bookA.getIsbn());
        Assertions.assertThat(result).isEmpty();
    }
}
