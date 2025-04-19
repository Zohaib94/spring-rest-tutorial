package com.example.database;

import com.example.database.config.RabbitMQConfig;
import com.example.database.domain.entities.Book;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log
public class DatabaseApplication {
	public static void main(String[] args) {
		SpringApplication.run(DatabaseApplication.class, args);
	}
}
