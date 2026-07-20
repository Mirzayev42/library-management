package com.library.management.mapper;

import com.library.management.dto.AuthorDto;
import com.library.management.model.Author;
import com.library.management.model.Book;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AuthorMapper {

    public AuthorDto toDto(Author author) {
        AuthorDto dto = new AuthorDto();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setBookTitles(author.getBooks().stream()
                .map(Book::getTitle)
                .collect(Collectors.toList()));
        return dto;
    }
}
