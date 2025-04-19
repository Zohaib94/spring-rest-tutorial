package com.example.database.listeners;

import com.example.database.config.RabbitMQConfig;
import com.example.database.domain.entities.Book;
import com.example.database.services.BookService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookQueueListener {
    @Autowired
    private BookService bookService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void processMessage(Book b) {
        b.setTitle(b.getTitle() + " - Processed");
        bookService.createBook(b.getIsbn(), b);
    }
}
