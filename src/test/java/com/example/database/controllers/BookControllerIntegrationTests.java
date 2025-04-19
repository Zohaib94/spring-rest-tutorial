package com.example.database.controllers;

import com.example.database.TestDataUtil;
import com.example.database.domain.entities.Author;
import com.example.database.domain.entities.Book;
import com.example.database.services.BookService;
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
    private BookService bookService;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.bookService = bookService;
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

    @Test
    public void assertSuccessonListFetch() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void assertAuthorListFetch() throws Exception {
        Book book = TestDataUtil.createTestBook(null);
        bookService.createBook(book.getIsbn(), book);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(book.getTitle())
        );
    }

    @Test
    public void assertBookFetchIsSuccess() throws Exception {
        final String isbn = "00123";
        Book book = TestDataUtil.createTestBook(null);
        bookService.createBook(isbn, book);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/books/" + isbn)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void assertBookIsFetched() throws Exception {
      final String isbn = "00123";
      Book book = TestDataUtil.createTestBook(null);
      bookService.createBook(isbn, book);

      mockMvc.perform(
              MockMvcRequestBuilders
                      .get("/books/" + isbn)
                      .contentType(MediaType.APPLICATION_JSON)
      ).andExpect(
          MockMvcResultMatchers.jsonPath("$.isbn").value(isbn)
      ).andExpect(
          MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle())
      );
    }

    @Test
    public void assertBookIsNotFetched() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/books/0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void assertBookIsUpdated() throws Exception {
        Book book = TestDataUtil.createTestBook(null);
        book = bookService.createBook("00123", book);

        book.setTitle("New title");
        String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void assertUpdatedBookIsReturned() throws Exception {
      Book book = TestDataUtil.createTestBook(null);
      book = bookService.createBook("00123", book);

      book.setTitle("New title");
      String bookJson = objectMapper.writeValueAsString(book);

      mockMvc.perform(
              MockMvcRequestBuilders
                      .put("/books/" + book.getIsbn())
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(bookJson)
      ).andExpect(
        MockMvcResultMatchers.jsonPath("$.isbn").isString()
      ).andExpect(
        MockMvcResultMatchers.jsonPath("$.title").value("New title")
      );
    }

    @Test
    public void assertBookIsUpdatedWithPartialUpdate() throws Exception {
        Book book = TestDataUtil.createTestBook(null);
        book = bookService.createBook("00123", book);

        book.setTitle("New title");
        String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .patch("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void assertUpdatedBookIsReturnedWithPartialUpdate() throws Exception {
      Book book = TestDataUtil.createTestBook(null);
      book = bookService.createBook("00123", book);

      book.setTitle("New title");
      String bookJson = objectMapper.writeValueAsString(book);

      mockMvc.perform(
              MockMvcRequestBuilders
                      .patch("/books/" + book.getIsbn())
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(bookJson)
      ).andExpect(
        MockMvcResultMatchers.jsonPath("$.isbn").isString()
      ).andExpect(
        MockMvcResultMatchers.jsonPath("$.title").value("New title")
      );
    }

    @Test
    public void assertBookIsDeleted() throws Exception {
        Book sourceBook = TestDataUtil.createTestBook(null);
        sourceBook = bookService.createBook("0123", sourceBook);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/books/" + sourceBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isNoContent()
        );
    }
}
