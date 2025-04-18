package com.example.database.controllers;

import com.example.database.TestDataUtil;
import com.example.database.domain.entities.Book;
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
public class BookControllerIntegrationTests {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void assertBookIsCreated() throws Exception {
        Book book = TestDataUtil.createTestBook(null);
        String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void assertCreatedBookIsReturned() throws Exception {
      Book book = TestDataUtil.createTestBook(null);
      String bookJson = objectMapper.writeValueAsString(book);

      mockMvc.perform(
              MockMvcRequestBuilders
                      .put("/books/" + book.getIsbn())
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(bookJson)
      ).andExpect(
        MockMvcResultMatchers.jsonPath("$.isbn").isString()
      ).andExpect(
        MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle())
      );
    }
}
