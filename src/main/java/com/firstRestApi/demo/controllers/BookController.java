package com.firstRestApi.demo.controllers;

import com.firstRestApi.demo.domain.dto.BookDto;
import com.firstRestApi.demo.domain.entities.BookEntity;
import com.firstRestApi.demo.mappers.Mapper;
import com.firstRestApi.demo.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private Mapper<BookEntity, BookDto> bookMapper;
    private BookService bookService;
    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto)
    {
        // create a book.
        // save the book.
        // return the book.
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBook = bookService.createBook(isbn, bookEntity);
        return new ResponseEntity<>(bookMapper.mapTo(savedBook),HttpStatus.CREATED);
    }

    @GetMapping(path = "/books")
    public List<BookDto> listBooks()
    {
        List<BookEntity> books = bookService.findAll();
        return books.stream().map(
                bookMapper::mapTo
        ).toList();
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn)
    {
        Optional<BookEntity> foundBook = bookService.findOne(isbn);
        return foundBook.map(
                bookEntity ->
                {
                    BookDto book = bookMapper.mapTo(bookEntity);
                    return new ResponseEntity<>(book, HttpStatus.OK);
                }
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
