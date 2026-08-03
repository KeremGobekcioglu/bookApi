package com.firstRestApi.demo.mappers.impl;

import com.firstRestApi.demo.domain.dto.AuthorDto;
import com.firstRestApi.demo.domain.entities.AuthorEntity;
import com.firstRestApi.demo.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto> {

    private ModelMapper modelMapper;

    public AuthorMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public AuthorDto mapTo(AuthorEntity author) {
        return modelMapper.map(author,AuthorDto.class);
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
    }
}
