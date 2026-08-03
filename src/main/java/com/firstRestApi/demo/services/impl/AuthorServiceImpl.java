package com.firstRestApi.demo.services.impl;

import com.firstRestApi.demo.domain.entities.AuthorEntity;
import com.firstRestApi.demo.repositories.AuthorRepository;
import com.firstRestApi.demo.services.AuthorService;
import org.springframework.stereotype.Service;

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
}
