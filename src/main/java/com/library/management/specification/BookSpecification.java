package com.library.management.specification;
import com.library.management.model.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<Book> hasTitle(String title) {
        return ((root, query, criteriaBuilder) ->
                title == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
    }

    public static Specification<Book> hasAuthorName(String authorName) {
        return ((root, query, criteriaBuilder) ->
                authorName == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("author").get("name")), "%" + authorName.toLowerCase() + "%"));
}

    public static Specification<Book> hasCategoryName(String categoryName) {
        return((root, query, criteriaBuilder) ->
                categoryName == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.join("categories").get("name")), "%" + categoryName.toLowerCase() + "%"));
    }
}
