package com.example.database.dao.implementation;

import com.example.database.TestDataUtil;
import com.example.database.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoImplementationIntegrationTest {

    private AuthorDaoImplementation underTest;

    @Autowired
    public AuthorDaoImplementationIntegrationTest(AuthorDaoImplementation underTest) {
        this.underTest = underTest;
    }

    @Test
    public void assertAuthorCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthor();

        underTest.create(author);
        Optional<Author> result = underTest.findOne(author.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void assertMultipleAuthorsAreCreatedAndLoaded() {
        Author authorA = TestDataUtil.createTestAuthor();
        Author authorB = TestDataUtil.createTestAuthorB();

        underTest.create(authorA);
        underTest.create(authorB);

        List<Author> authorList = underTest.find();

        assertThat(authorList).hasSize(2);
        assertThat(authorList).containsExactly(authorA, authorB);
    }

    @Test
    public void assertAuthorCanBeUpdated() {
        Author authorA = TestDataUtil.createTestAuthor();
        underTest.create(authorA);

        authorA.setName("Updated");
        underTest.update(authorA.getId(), authorA);

        Optional<Author> result = underTest.findOne(authorA.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorA);
    }
}
