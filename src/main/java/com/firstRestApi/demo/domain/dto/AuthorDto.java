package com.firstRestApi.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor // this is needed for jackson since it uses getters and setters to modfiy object after creating it with no args
@Builder
public class AuthorDto {

    private Long id;
    private String name;
    private Integer age;
}
