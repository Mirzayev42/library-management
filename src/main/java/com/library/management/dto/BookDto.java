package com.library.management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookDto {

    private Long id;
    @NotBlank(message = "Kitabın adı boş ola bilməz")
    private String title;
    private Long authorId;
    private AuthorDto author;
}

