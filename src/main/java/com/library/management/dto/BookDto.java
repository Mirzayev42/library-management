package com.library.management.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long id;
    @NotBlank(message = "Kitabın adı boş ola bilməz")
    private String title;
    @NotNull(message = "Müəllif ID-si qeyd olunmalıdır")
    private Long authorId;
    private AuthorDto author;

}

