package com.firstRestApi.demo.services.impl;

import com.firstRestApi.demo.domain.entities.AuthorEntity;
import com.firstRestApi.demo.repositories.AuthorRepository;
import com.firstRestApi.demo.services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity author) {
        return authorRepository.save(author);
    }

    @Override
    public List<AuthorEntity> findAll() {
        Iterable<AuthorEntity> iterableAuthors = authorRepository.findAll();
        List<AuthorEntity> list = new ArrayList<>();
        iterableAuthors.forEach(list::add);
        //return list;
        return StreamSupport.stream(iterableAuthors.spliterator(),false)
                .toList();
    }

    @Override
    public Optional<AuthorEntity> findOne(Long id) {
        return authorRepository.findById(id);
    }
}
