package com.library.management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.library.management.dto.BookDto;
import com.library.management.mapper.BookMapper;
import com.library.management.model.Author;
import com.library.management.model.Book;
import com.library.management.repository.AuthorRepository;
import com.library.management.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @Test
    void testCreateBook() {

        BookDto dto = new BookDto();
        dto.setTitle("Test Kitabı");
        dto.setAuthorId(1L);

        Author author = new Author(1L, "Test Müəllif", null);

        Book entity = new Book();
        entity.setId(1L);
        entity.setTitle("Test Kitabı");
        entity.setAuthor(author);

        BookDto expectedDto = new BookDto();
        expectedDto.setId(1L);
        expectedDto.setTitle("Test Kitabı");


        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookMapper.toEntity(any(BookDto.class), any(Author.class))).thenReturn(entity);
        when(bookRepository.save(any(Book.class))).thenReturn(entity);
        when(bookMapper.toDto(any(Book.class))).thenReturn(expectedDto);

        BookDto result = bookService.createBook(dto);

        assertNotNull(result);
        assertEquals("Test Kitabı", result.getTitle());
        verify(authorRepository, times(1)).findById(1L); // Müəllifin axtarıldığını yoxlayırıq
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(bookMapper, times(1)).toDto(any(Book.class));
    }
}