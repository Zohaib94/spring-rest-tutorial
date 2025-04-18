package com.example.database.dao.implementation;

import com.example.database.TestDataUtil;
import com.example.database.dao.AuthorDao;
import com.example.database.domain.Author;
import com.example.database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BookDaoImplementationIntegrationTest {

    private BookDaoImplementation underTest;
    private AuthorDao authorDao;

    @Autowired
    public BookDaoImplementationIntegrationTest(BookDaoImplementation underTest, AuthorDao authorDao) {
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void assertBookCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthor();
        authorDao.create(author);

        Book book = TestDataUtil.createTestBook();
        book.setAuthorId(author.getId());
        underTest.create(book);

        Optional<Book> result = underTest.findOne(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }
}
