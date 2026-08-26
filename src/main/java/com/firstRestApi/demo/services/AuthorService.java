package com.firstRestApi.demo.services;

import com.firstRestApi.demo.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorEntity saveAuthor(AuthorEntity author);
    List<AuthorEntity> findAll();
    Optional<AuthorEntity> findOne(Long id);

    boolean isExists(Long id);
}
