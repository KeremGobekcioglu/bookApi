package com.firstRestApi.demo.mappers;

public interface Mapper<Entity,Dto> {

    // Converts an object of type A to type B
    Dto mapTo(Entity a);
    // Converts an object of type B back to type A
    Entity mapFrom(Dto b);
}
