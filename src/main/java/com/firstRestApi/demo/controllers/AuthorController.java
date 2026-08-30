package com.firstRestApi.demo.controllers;

import com.firstRestApi.demo.domain.dto.AuthorDto;
import com.firstRestApi.demo.domain.entities.AuthorEntity;
import com.firstRestApi.demo.mappers.Mapper;
import com.firstRestApi.demo.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        AuthorEntity savedAuthor  = authorService.saveAuthor(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthor) , HttpStatus.CREATED);
    }

    @GetMapping(path = "/authors")
    public List<AuthorDto> listAuthors()
    {
        List<AuthorEntity> list = authorService.findAll();
//        return list.stream().map(
//                authorMapper::mapTo
//        ).collect(Collectors.toList());
        List<AuthorDto> authorDtos = new ArrayList<>();
        for(AuthorEntity author : list)
        {
            authorDtos.add(authorMapper.mapTo(author));
        }
        return authorDtos;
    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id )
    {
        Optional<AuthorEntity> foundAuthor = authorService.findOne(id);
        return foundAuthor.map(
                authorEntity -> {
                    AuthorDto authorDto = authorMapper.mapTo(authorEntity);
                    return new ResponseEntity<>(authorDto, HttpStatus.OK);
                }
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(
            @PathVariable("id") Long id,
             @RequestBody AuthorDto authorDto
    )
    {
        if(!authorService.isExists(id))
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // force the id from the path, ignoring any id in the body, so save() updates this row
        authorDto.setId(id);
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity savedAuthorEntity = authorService.saveAuthor(authorEntity);
        AuthorDto savedDto = authorMapper.mapTo(savedAuthorEntity);
        return new ResponseEntity<>(
                savedDto,
                HttpStatus.OK
        );
    }

    @PatchMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdate(
            @PathVariable("id") Long id,
            @RequestBody AuthorDto authorDto
    )
    {
        if(!authorService.isExists(id))
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity updatedAuthorEntity = authorService.partialUpdate(id, authorEntity);
        AuthorDto updatedAuthorDto = authorMapper.mapTo(updatedAuthorEntity);
        return new ResponseEntity<>(updatedAuthorDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity deleteAuthor(
            @PathVariable("id") Long id
    )
    {
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
