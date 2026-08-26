package com.firstRestApi.demo.services.impl;

import com.firstRestApi.demo.domain.dto.AuthorDto;
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
    public AuthorEntity saveAuthor(AuthorEntity author) {
        // save() inserts if id is null, updates if id is set (see isExists check in controller)
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

    @Override
    public boolean isExists(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(Long id, AuthorEntity author) {
        author.setId(id);
        return authorRepository.findById(id)
                .map(
                        existingAuthor ->
                        {
                            Optional.ofNullable(author.getAge()).ifPresent(existingAuthor::setAge);
                            Optional.ofNullable(author.getName()).ifPresent(existingAuthor::setName);
                            return authorRepository.save(existingAuthor);
                        }
                ).orElseThrow(
                        () -> new RuntimeException("Author Does Not exists")
                );
    }
}
