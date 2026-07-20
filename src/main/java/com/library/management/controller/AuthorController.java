package com.library.management.controller;
import com.library.management.dto.AuthorDto;
import com.library.management.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorDto> create(@RequestBody AuthorDto dto) {
        return new ResponseEntity<>(authorService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<AuthorDto>> getAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(authorService.getAll(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> update(@PathVariable Long id, @RequestBody AuthorDto dto) {
        return ResponseEntity.ok(authorService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
