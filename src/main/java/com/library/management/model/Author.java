package com.library.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Bos qala bilmez name")
    private String name;
    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();
}
