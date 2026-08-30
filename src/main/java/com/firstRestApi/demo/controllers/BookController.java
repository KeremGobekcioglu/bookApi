package com.firstRestApi.demo.controllers;

import com.firstRestApi.demo.domain.dto.BookDto;
import com.firstRestApi.demo.domain.entities.BookEntity;
import com.firstRestApi.demo.mappers.Mapper;
import com.firstRestApi.demo.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto)
    {
        // create a book.
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        // we need to check whether book exists or not BEFORE creating/saving the book. otherwise it will always exists.
        boolean isExists = bookService.isExists(isbn);
        // save the book.
        BookEntity savedBookEntity = bookService.createUpdateBook(isbn, bookEntity);
        BookDto savedUpdatedBook = bookMapper.mapTo(savedBookEntity);
        if(isExists) // then this is an update.
            return new ResponseEntity<>(savedUpdatedBook,HttpStatus.OK);
        else
            return new ResponseEntity<>(savedUpdatedBook,HttpStatus.CREATED);
    }

    @GetMapping(path = "/books")
    public Page<BookDto> listBooks(Pageable pageable)
    {
        Page<BookEntity> books = bookService.findAll(pageable);
        return books.map(bookMapper::mapTo);
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

    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity deleteBook(
            @PathVariable("isbn") String isbn
    )
    {
        bookService.delete(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
