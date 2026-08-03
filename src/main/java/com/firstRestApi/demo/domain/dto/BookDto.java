package com.firstRestApi.demo.domain.dto;

import com.firstRestApi.demo.domain.entities.AuthorEntity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {

    private String isbn;
    private String title;
    private AuthorDto author;
}
