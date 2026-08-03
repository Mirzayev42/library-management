package com.library.management.service;

import com.library.management.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
public class BookServiceIntegrationTest {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testTransactionRollbackOnException() {
        long initialCount = bookRepository.count();

        assertThatThrownBy(() -> {
            bookService.createBookWithAuthorAndCategory(null, "Test Author", "Test Category");
        }).isInstanceOf(Exception.class);

        long finalCount = bookRepository.count();
        assertThat(finalCount).isEqualTo(initialCount);
    }
}
