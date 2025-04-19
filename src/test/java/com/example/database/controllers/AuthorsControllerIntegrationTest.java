package com.example.database.controllers;

import com.example.database.TestDataUtil;
import com.example.database.domain.dto.AuthorDto;
import com.example.database.domain.entities.Author;
import com.example.database.services.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorsControllerIntegrationTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private AuthorService authorService;

    @Autowired
    public AuthorsControllerIntegrationTest(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.authorService = authorService;
    }

    @Test
    public void assertAuthorIsCreated() throws Exception {
        Author author = TestDataUtil.createTestAuthor();
        author.setId(null);

        String authorJson = objectMapper.writeValueAsString(author);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void assertCreatedAuthorIsReturned() throws Exception {
        Author author = TestDataUtil.createTestAuthor();
        author.setId(null);

        String authorJson = objectMapper.writeValueAsString(author);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(author.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(author.getAge())
        );
    }

    @Test
    public void assertSuccessonListFetch() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void assertAuthorListFetch() throws Exception {
        Author author = TestDataUtil.createTestAuthor();
        authorService.saveAuthor(author);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(author.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(author.getAge())
        );
    }

    @Test
    public void assertAuthorFetchIsSuccess() throws Exception {
        Author author = TestDataUtil.createTestAuthor();
        author = authorService.saveAuthor(author);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/authors/" + author.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void assertAuthorIsFetched() throws Exception {
        Author author = TestDataUtil.createTestAuthor();
        author = authorService.saveAuthor(author);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/authors/" + author.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(author.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(author.getAge())
        );
    }

    @Test
    public void assertAuthorIsNotFetched() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/authors/0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void assertAuthorIsUpdated() throws Exception {
        Author sourceAuthor = TestDataUtil.createTestAuthor();
        authorService.saveAuthor(sourceAuthor);

        AuthorDto targetAuthorDto = TestDataUtil.createTestAuthorDto();
        targetAuthorDto.setId(sourceAuthor.getId());
        targetAuthorDto.setName("Updated Name");

        String targetAuthorJson = objectMapper.writeValueAsString(targetAuthorDto);
        
        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/authors/" + sourceAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(targetAuthorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(sourceAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Updated Name")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(targetAuthorDto.getAge())
        );
    }

    @Test
    public void assertAuthorIsNotFoundDuringUpdate() throws Exception {
        Author sourceAuthor = TestDataUtil.createTestAuthor();
        authorService.saveAuthor(sourceAuthor);

        AuthorDto targetAuthorDto = TestDataUtil.createTestAuthorDto();
        targetAuthorDto.setId(sourceAuthor.getId());
        targetAuthorDto.setName("Updated Name");

        String targetAuthorJson = objectMapper.writeValueAsString(targetAuthorDto);
        
        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/authors/" + 0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(targetAuthorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void assertAuthorIsUpdatedWithPatchEndpoint() throws Exception {
        Author sourceAuthor = TestDataUtil.createTestAuthor();
        authorService.saveAuthor(sourceAuthor);

        AuthorDto targetAuthorDto = TestDataUtil.createTestAuthorDto();
        targetAuthorDto.setId(sourceAuthor.getId());
        targetAuthorDto.setName("Updated Name");

        String targetAuthorJson = objectMapper.writeValueAsString(targetAuthorDto);
        
        mockMvc.perform(
                MockMvcRequestBuilders
                        .patch("/authors/" + sourceAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(targetAuthorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(sourceAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Updated Name")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(targetAuthorDto.getAge())
        );
    }

    @Test
    public void assertAuthorIsNotFoundDuringUpdateWithPatchEndpoint() throws Exception {
        Author sourceAuthor = TestDataUtil.createTestAuthor();
        authorService.saveAuthor(sourceAuthor);

        AuthorDto targetAuthorDto = TestDataUtil.createTestAuthorDto();
        targetAuthorDto.setId(sourceAuthor.getId());
        targetAuthorDto.setName("Updated Name");

        String targetAuthorJson = objectMapper.writeValueAsString(targetAuthorDto);
        
        mockMvc.perform(
                MockMvcRequestBuilders
                        .patch("/authors/" + 0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(targetAuthorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void assertAuthorIsDeleted() throws Exception {
        Author sourceAuthor = TestDataUtil.createTestAuthor();
        sourceAuthor = authorService.saveAuthor(sourceAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/authors/" + sourceAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isNoContent()
        );
    }
}
