package com.firstRestApi.demo.services;

import com.firstRestApi.demo.domain.entities.BookEntity;

public interface BookService {
    BookEntity createBook(String isbn, BookEntity book);
}
