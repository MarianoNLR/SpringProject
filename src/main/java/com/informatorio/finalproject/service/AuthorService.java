package com.informatorio.finalproject.service;

import com.informatorio.finalproject.dto.AuthorDTO;
import com.informatorio.finalproject.entity.Author;
import com.informatorio.finalproject.repository.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorDTO getAuthor(Long id){
        return authorRepository.findById(id).map(this::toDTO).get();
    }

    private AuthorDTO toDTO(Author author){
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setFirstName(author.getFirstName());
        authorDTO.setLastName(author.getLastName());
        authorDTO.setFullName(author.getFullName());
        authorDTO.setCreatedAt(author.getCreatedAt());

        return authorDTO;
    }
}
