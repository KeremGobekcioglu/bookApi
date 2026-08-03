package com.firstRestApi.demo.services.impl;

import com.firstRestApi.demo.domain.entities.BookEntity;
import com.firstRestApi.demo.repositories.BookRepository;
import com.firstRestApi.demo.services.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(String isbn, BookEntity book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }
}
