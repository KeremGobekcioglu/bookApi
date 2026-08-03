package com.firstRestApi.demo.controllers;

import com.firstRestApi.demo.domain.dto.AuthorDto;
import com.firstRestApi.demo.domain.entities.AuthorEntity;
import com.firstRestApi.demo.mappers.Mapper;
import com.firstRestApi.demo.services.AuthorService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    private AuthorService authorService;

    private Mapper<AuthorEntity,AuthorDto> authorMapper;
    public AuthorController(AuthorService service , Mapper<AuthorEntity,AuthorDto> authorMapper)
    {
        this.authorService = service;
        this.authorMapper = authorMapper;
    }
    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author)
    {
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthor  = authorService.createAuthor(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthor) , HttpStatus.CREATED);
    }
}
