package com.library.management.repository;

import com.library.management.model.Author;
import com.library.management.model.Book;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long>  , JpaSpecificationExecutor<Book> {
    List<Book> findByAuthor(Author author);

    List<Book> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT b FROM Book b WHERE LOWER(b.author.name) LIKE LOWER(CONCAT('%', :authorName, '%'))")
    List<Book> findBooksByAuthorName(@Param("authorName") String authorName);

    @Query("SELECT DISTINCT b FROM Book b JOIN b.categories c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :categoryName, '%'))")
    List<Book> findBooksByCategoryName(@Param("categoryName") String categoryName);

    @Override
    @EntityGraph(attributePaths = {"author", "categories"})
    @NonNull
    List<Book> findAll();
}

