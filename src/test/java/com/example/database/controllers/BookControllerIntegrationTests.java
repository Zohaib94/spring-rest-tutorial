package com.example.database.controllers;

import com.example.database.BaseIntegrationTest;
import com.example.database.TestDataUtil;
import com.example.database.config.RabbitMQConfig;
import com.example.database.domain.entities.Book;
import com.example.database.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControllerIntegrationTests extends BaseIntegrationTest {
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    private ObjectMapper objectMapper;
    
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
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
                MockMvcResultMatchers.jsonPath("$.content[0].isbn").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].title").value(book.getTitle())
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

    @Test
    public void assertBookMessageIsSentAndReceived() throws Exception {
        // Create and save a book
        Book book = TestDataUtil.createTestBook(null);
        book = bookService.createBook("00123", book);
        
        // Send the message
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_NAME,
            RabbitMQConfig.ROUTING_KEY,
            book
        );
        
        Thread.sleep(1000); // Wait for 1 second
        
        var retrievedBook = bookService.findByIsbn(book.getIsbn());
        assertTrue(retrievedBook.isPresent(), "Book should be found in the database");
        assertEquals(book.getTitle() + " - Processed", retrievedBook.get().getTitle(), 
            "Book title in database should match the sent book title");
    }
}
