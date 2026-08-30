package com.firstRestApi.demo.services.impl;

import com.firstRestApi.demo.domain.entities.BookEntity;
import com.firstRestApi.demo.repositories.BookRepository;
import com.firstRestApi.demo.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createUpdateBook(String isbn, BookEntity book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }

    @Override
    public List<BookEntity> findAll() {
        /*
         * CrudRepository.findAll() returns an Iterable, not a List.
         *
         * StreamSupport creates a Stream from sources that don't already provide one
         * (such as Iterable).
         *
         * spliterator() returns a Spliterator, which is an object used to traverse
         * and optionally split the elements for parallel processing.
         *
         * The second argument (false) means "use a sequential stream"
         * (true would create a parallel stream).
         */
        return StreamSupport.stream(bookRepository.findAll().spliterator(),false).toList();
    }

    @Override
    public Page<BookEntity> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Optional<BookEntity> findOne(String isbn)
    {
        return bookRepository.findById(isbn);
    }

    @Override
    public boolean isExists(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public void delete(String isbn) {
        bookRepository.deleteById(isbn);
    }
}
