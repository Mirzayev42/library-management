package com.library.management.service;
import com.library.management.dto.BookDto;
import com.library.management.mapper.BookMapper;
import com.library.management.model.Author;
import com.library.management.model.Book;
import com.library.management.model.Category;
import com.library.management.repository.AuthorRepository;
import com.library.management.repository.BookRepository;
import com.library.management.repository.CategoryRepository;
import com.library.management.specification.BookSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class  BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public BookDto createBook(BookDto bookDto) {
        Author author = findOrCreateAuthor(bookDto);

        Book book = bookMapper.toEntity(bookDto, author);

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional
    public BookDto updateBook(Long id, BookDto bookDto) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Kitab tapılmadı: " + id));

        Author author = findOrCreateAuthor(bookDto);

        existingBook.setTitle(bookDto.getTitle());
        existingBook.setAuthor(author);

        return bookMapper.toDto(bookRepository.save(existingBook));
    }

    private Author findOrCreateAuthor(BookDto dto) {
        if (dto.getAuthorId() != null) {
            return authorRepository.findById(dto.getAuthorId())
                    .orElseThrow(() -> new EntityNotFoundException("Müəllif tapılmadı"));
        } else if (dto.getAuthor() != null && dto.getAuthor().getName() != null) {
            return authorRepository.findByName(dto.getAuthor().getName())
                    .orElseGet(() -> {
                        Author newAuthor = new Author();
                        newAuthor.setName(dto.getAuthor().getName());
                        return authorRepository.save(newAuthor);
                    });
        }
        throw new IllegalArgumentException("Kitab üçün müəllif məlumatı mütləq qeyd edilməlidir!");

    }

    public List<BookDto> getBooksByAuthorId(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Müəllif tapılmadı"));
        return bookRepository.findByAuthor(author).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }


    public Page<BookDto> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::toDto);
    }

    public BookDto getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Kitab tapılmadı: " + id));
    }

    public void deleteBookById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Silmək üçün kitab tapılmadı!");
        }
        bookRepository.deleteById(id);
    }

    public List<BookDto> getBooksByTitle(String title) {
       return bookRepository.findByTitleContainingIgnoreCase(title).
                stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }
    public List<BookDto> getBooksByAuthorName(String authorName) {
        return bookRepository.findBooksByAuthorName(authorName).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }
    public List<BookDto> getBooksByCategoryName(String categoryName) {
        return bookRepository.findBooksByCategoryName(categoryName).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> searchBooks(String title, String authorName, String categoryName){
        Specification<Book> spec =  Specification.where(BookSpecification.hasTitle(title))
                .and(BookSpecification.hasAuthorName(authorName))
                .and(BookSpecification.hasCategoryName(categoryName));

        return bookRepository.findAll(spec).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookDto createBookWithAuthorAndCategory(String bookTitle, String authorName, String categoryName){
        Author author = authorRepository.findByName(authorName)
                .orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setName(authorName);
                    return authorRepository.save(newAuthor);
                });


        Category category;
        if (categoryRepository.existsByName(categoryName)) {
            category = categoryRepository.findByName(categoryName).get();
        } else {
            Category newCategory = new Category();
            newCategory.setName(categoryName);
            category = categoryRepository.save(newCategory);
        }


                Book book = new Book();
                book.setTitle(bookTitle);
                book.setAuthor(author);
                book.setCategories(Set.of(category));
                Book bookSaved = bookRepository.save(book);
                return bookMapper.toDto(bookSaved);
    }
}
