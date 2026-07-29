package com.library.management.mapper;
import com.library.management.dto.AuthorDto;
import com.library.management.model.Author;
import com.library.management.model.Book;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AuthorMapper {


    public AuthorDto toDto(Author author) {
        if (author == null) {
            return null;
        }

        AuthorDto dto = new AuthorDto();
        dto.setId(author.getId());
        dto.setName(author.getName());

        if (author.getBooks() != null) {
            dto.setBookTitles(author.getBooks().stream()
                    .map(Book::getTitle)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
