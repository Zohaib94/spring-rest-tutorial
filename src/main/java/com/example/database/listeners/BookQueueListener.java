package com.example.database.listeners;

import com.example.database.config.RabbitMQConfig;
import com.example.database.domain.entities.Book;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class BookQueueListener {
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void processMessage(Book b) {
        System.out.println("*************************");
        System.out.println(b.getTitle());
        System.out.println("*************************");
    }
}
