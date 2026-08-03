package com.firstRestApi.demo.mappers.impl;


import com.firstRestApi.demo.domain.dto.BookDto;
import com.firstRestApi.demo.domain.entities.BookEntity;
import com.firstRestApi.demo.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Component
public class BookMapperImpl implements Mapper<BookEntity, BookDto>
{
    private ModelMapper modelMapper;

    public BookMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto mapTo(BookEntity a) {
        return modelMapper.map(a,BookDto.class);
    }

    @Override
    public BookEntity mapFrom(BookDto b) {
        return modelMapper.map(b,BookEntity.class);
    }
}