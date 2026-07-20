package com.library.management.service;

import com.library.management.dto.AuthorDto;
import com.library.management.mapper.AuthorMapper;
import com.library.management.model.Author;
import com.library.management.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Transactional
    public AuthorDto create(AuthorDto dto) {
        Author author = new Author(dto.getName());
        return authorMapper.toDto(authorRepository.save(author));
    }

    public Page<AuthorDto> getAll(Pageable pageable) {
        return authorRepository.findAll(pageable).map(authorMapper::toDto);
    }

    public AuthorDto getById(Long id) {
        return authorRepository.findById(id).map(authorMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Müəllif tapılmadı"));
    }

    @Transactional
    public AuthorDto update(Long id, AuthorDto dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Müəllif tapılmadı"));
        author.setName(dto.getName());
        return authorMapper.toDto(authorRepository.save(author));
    }

    @Transactional
    public void delete(Long id) {
        if (!authorRepository.existsById(id)) throw new EntityNotFoundException("Tapılmadı");
        authorRepository.deleteById(id);
    }
}
