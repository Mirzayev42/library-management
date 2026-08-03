package com.library.management.mapper;
import com.library.management.dto.BookDto;
import com.library.management.model.Author;
import com.library.management.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final AuthorMapper authorMapper;
    private final CategoryMapper categoryMapper;

    public BookDto toDto(Book book) {
        if (book == null) {
            return null;
        }

        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());

        if (book.getAuthor() != null) {
            dto.setAuthorId(book.getAuthor().getId());
            dto.setAuthor(authorMapper.toDto(book.getAuthor()));
        }

        if (book.getCategories() != null) {
            dto.setCategories(book.getCategories().stream()
                    .map(categoryMapper::toDto)
                    .collect(Collectors.toSet()));
        }

        return dto;
    }

    public Book toEntity(BookDto bookDto, Author author) {
        if (bookDto == null) {
            return null;
        }

        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(author);
        return book;
    }
}