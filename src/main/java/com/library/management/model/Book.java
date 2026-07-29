package com.library.management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_title",nullable = false, length = 100)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "author_id", nullable = false)
    private Author author;
}
