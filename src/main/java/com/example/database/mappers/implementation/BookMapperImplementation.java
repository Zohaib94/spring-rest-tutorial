package com.example.database.mappers.implementation;

import org.modelmapper.ModelMapper;

import com.example.database.domain.dto.BookDto;
import com.example.database.domain.entities.Book;
import com.example.database.mappers.Mapper;

public class BookMapperImplementation implements Mapper<Book, BookDto> {
  private ModelMapper modelMapper;

  public BookMapperImplementation(ModelMapper modelMapper) {
      this.modelMapper = modelMapper;
  }

  @Override
  public BookDto mapTo(Book bookEntity) {
    return modelMapper.map(bookEntity, BookDto.class);
  }

  @Override
  public Book mapFrom(BookDto bookDto) {
    return modelMapper.map(bookDto, Book.class);
  }
}
