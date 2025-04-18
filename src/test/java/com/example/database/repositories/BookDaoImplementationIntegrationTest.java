//package com.example.database.repositories;
//
//import com.example.database.TestDataUtil;
//import com.example.database.dao.AuthorDao;
//import com.example.database.domain.Author;
//import com.example.database.domain.Book;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//public class BookDaoImplementationIntegrationTest {
//
//    private BookDaoImplementation underTest;
//    private AuthorDao authorDao;
//
//    @Autowired
//    public BookDaoImplementationIntegrationTest(BookDaoImplementation underTest, AuthorDao authorDao) {
//        this.underTest = underTest;
//        this.authorDao = authorDao;
//    }
//
//    @Test
//    public void assertBookCreatedAndRecalled() {
//        Author author = TestDataUtil.createTestAuthor();
//        authorDao.create(author);
//
//        Book book = TestDataUtil.createTestBook();
//        book.setAuthorId(author.getId());
//        underTest.create(book);
//
//        Optional<Book> result = underTest.findOne(book.getIsbn());
//
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(book);
//    }
//
//    @Test
//    public void assertMultipleBooksAreCreatedAndLoaded() {
//        Author author = TestDataUtil.createTestAuthor();
//        authorDao.create(author);
//
//        Book bookA = TestDataUtil.createTestBook();
//        bookA.setAuthorId(author.getId());
//
//        Book bookB = TestDataUtil.createTestBookB();
//        bookB.setAuthorId(author.getId());
//
//        underTest.create(bookA);
//        underTest.create(bookB);
//
//        List<Book> bookList = underTest.find();
//
//        Assertions.assertThat(bookList).hasSize(2);
//        Assertions.assertThat(bookList).containsExactly(bookA, bookB);
//    }
//
//    @Test
//    public void assertBookIsUpdated() {
//        Author author = TestDataUtil.createTestAuthor();
//        authorDao.create(author);
//
//        Book bookA = TestDataUtil.createTestBook();
//        bookA.setAuthorId(author.getId());
//
//        underTest.create(bookA);
//
//        bookA.setTitle("Updated");
//        underTest.update(bookA.getIsbn(), bookA);
//
//        Optional<Book> result = underTest.findOne(bookA.getIsbn());
//
//        Assertions.assertThat(result).isPresent();
//        Assertions.assertThat(result.get()).isEqualTo(bookA);
//    }
//
//    @Test
//    public void assertBookIsDeleted() {
//        Author author = TestDataUtil.createTestAuthor();
//        authorDao.create(author);
//
//        Book bookA = TestDataUtil.createTestBook();
//        bookA.setAuthorId(author.getId());
//
//        underTest.delete(bookA.getIsbn());
//
//        Optional<Book> result = underTest.findOne(bookA.getIsbn());
//
//        Assertions.assertThat(result).isEmpty();
//    }
//}
