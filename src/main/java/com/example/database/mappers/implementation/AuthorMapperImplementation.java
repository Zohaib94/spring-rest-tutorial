package com.example.database.mappers.implementation;

import com.example.database.domain.dto.AuthorDto;
import com.example.database.domain.entities.Author;
import com.example.database.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapperImplementation implements Mapper<Author, AuthorDto> {
    private ModelMapper modelMapper;

    public AuthorMapperImplementation(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto mapTo(Author authorEntity) {
        return modelMapper.map(authorEntity, AuthorDto.class);
    }

    @Override
    public Author mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, Author.class);
    }
}
