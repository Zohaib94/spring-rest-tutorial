package com.example.database.dao.implementation;

import com.example.database.TestDataUtil;
import com.example.database.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
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
}
