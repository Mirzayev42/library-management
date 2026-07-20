package com.library.management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.library.management.dto.BookDto;
import com.library.management.mapper.BookMapper;
import com.library.management.model.Book;
import com.library.management.repository.AuthorRepository;
import com.library.management.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
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
    @Mock private BookRepository bookRepository;
    @Mock private AuthorRepository authorRepository;
    @Mock private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @Test
    void testCreateBook() {

        BookDto dto = new BookDto();
        dto.setTitle("Test Kitabı");

        Book book = new Book();
        book.setTitle("Test Kitabı");

        when(bookMapper.toEntity(any(BookDto.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toDto(any(Book.class))).thenReturn(dto);


        BookDto result = bookService.createBook(dto);


        assertNotNull(result);
        assertEquals("Test Kitabı", result.getTitle());

        // Yoxlama: Bütün vacib metodların çağrıldığından əmin ol
        verify(bookMapper).toEntity(dto);
        verify(bookRepository).save(book);
        verify(bookMapper).toDto(book);
    }


    @Test
    void testCreateBook_WhenAuthorDoesNotExist_ShouldThrowException() {
        BookDto dto = new BookDto();
        dto.setTitle("Test Kitabı");
        dto.setAuthorId(99L);


        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            bookService.createBook(dto);
        });

        verify(authorRepository, times(1)).findById(99L);
    }
}