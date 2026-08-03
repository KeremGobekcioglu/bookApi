package com.firstRestApi.demo.services;

import com.firstRestApi.demo.domain.entities.AuthorEntity;

public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity author);
}
