package com.library.management.mapper;
import com.library.management.dto.AuthorDto;
import com.library.management.dto.BookDto;
import com.library.management.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {
    private final AuthorMapper authorMapper;

    public BookDto toDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        if (book.getAuthor() != null) {
            dto.setAuthorId(book.getAuthor().getId());
            dto.setAuthor(authorMapper.toDto(book.getAuthor()));
        }
        return dto;
    }

    public Book toEntity(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        return book;
    }
}