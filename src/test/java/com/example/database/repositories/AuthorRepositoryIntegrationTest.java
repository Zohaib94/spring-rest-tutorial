package com.example.database.repositories;

import com.example.database.TestDataUtil;
import com.example.database.config.TestContainersConfig;
import com.example.database.domain.entities.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTest extends TestContainersConfig {
    private AuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTest(AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void assertAuthorCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthor();
        underTest.save(author);
        Optional<Author> result = underTest.findById(author.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void assertMultipleAuthorsAreCreatedAndLoaded() {
        Author authorA = TestDataUtil.createTestAuthor();
        Author authorB = TestDataUtil.createTestAuthorB();

        underTest.save(authorA);
        underTest.save(authorB);

        Iterable<Author> authorList = underTest.findAll();

        assertThat(authorList).hasSize(2);
        assertThat(authorList).containsExactly(authorA, authorB);
    }

    @Test
    public void assertAuthorCanBeUpdated() {
        Author authorA = TestDataUtil.createTestAuthor();
        underTest.save(authorA);

        authorA.setName("Updated");
        underTest.save(authorA);

        Optional<Author> result = underTest.findById(authorA.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorA);
    }

    @Test
    public void assertAuthorIsDeleted() {
        Author authorA = TestDataUtil.createTestAuthor();
        underTest.save(authorA);

        underTest.deleteById(authorA.getId());

        Optional<Author> result = underTest.findById(authorA.getId());

        assertThat(result).isEmpty();
    }

    @Test
    public void assertAuthorsBelowAge() {
        Author authorA = TestDataUtil.createTestAuthor();
        Author authorB = TestDataUtil.createTestAuthor();
        Author authorC = TestDataUtil.createTestAuthor();

        authorA.setAge(40);
        authorB.setAge(30);
        authorC.setAge(39);

        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);

        Iterable<Author> authors = underTest.ageLessThan(40);

        assertThat(authors).containsExactly(authorB, authorC);
    }

    @Test
    public void assertAuthorsAboveAge() {
        Author authorA = TestDataUtil.createTestAuthor();
        Author authorB = TestDataUtil.createTestAuthor();
        Author authorC = TestDataUtil.createTestAuthor();

        authorA.setAge(40);
        authorB.setAge(30);
        authorC.setAge(39);

        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);

        Iterable<Author> authors = underTest.findAuthorsAboveAge(39);

        assertThat(authors).containsExactly(authorA);
    }
}
