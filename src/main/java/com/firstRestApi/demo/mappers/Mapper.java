package com.firstRestApi.demo.mappers;

import com.firstRestApi.demo.domain.dto.BookDto;
import com.firstRestApi.demo.domain.entities.BookEntity;

public interface Mapper<Entity,Dto> {

    // Converts an object of type A to type B
    Dto mapTo(Entity a);
    // Converts an object of type B back to type A
    Entity mapFrom(Dto b);
}
