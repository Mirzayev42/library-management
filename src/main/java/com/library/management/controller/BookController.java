package com.library.management.controller;
import com.library.management.dto.BookDto;
import com.library.management.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<Page<BookDto>> getAll(@ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(bookService.getAllBooks(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<BookDto>> getBooksByAuthor(@PathVariable Long authorId) {
        return ResponseEntity.ok(bookService.getBooksByAuthorId(authorId));
    }

    @PostMapping
    public ResponseEntity<BookDto> create(@Valid @RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookService.createBook(bookDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> update(@PathVariable Long id, @Valid @RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookDto>> getBooksByTitle(@RequestParam String title) {
        return ResponseEntity.ok(bookService.getBooksByTitle(title));
    }

    @GetMapping("/filter/author")
    public ResponseEntity<List<BookDto>> getBooksByAuthorName(@RequestParam String authorName) {
        return ResponseEntity.ok(bookService.getBooksByAuthorName(authorName));
    }

    @GetMapping("/filter/category")
    public ResponseEntity<List<BookDto>> getBooksByCategoryName(@RequestParam String categoryName) {
        return ResponseEntity.ok(bookService.getBooksByCategoryName(categoryName));
    }

    @GetMapping("/dynamic-search")
    public ResponseEntity<List<BookDto>> dynamicSearch(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) String categoryName) {
        return ResponseEntity.ok(bookService.searchBooks(title, authorName, categoryName));
    }

    @PostMapping("/with-author-and-category")
    public ResponseEntity<BookDto> createBookWithAuthorAndCategory(
            @RequestParam String bookTitle,
            @RequestParam String authorName,
            @RequestParam String categoryName) {

        BookDto createdBook = bookService.createBookWithAuthorAndCategory(bookTitle, authorName, categoryName);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }
}



