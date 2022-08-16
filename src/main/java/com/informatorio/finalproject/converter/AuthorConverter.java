package com.informatorio.finalproject.converter;

import com.informatorio.finalproject.dto.AuthorDTO;
import com.informatorio.finalproject.entity.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter {


    public Author toEntity(AuthorDTO authorDTO){
        Author author = new Author();
        author.setId(authorDTO.getId());
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setFullName(authorDTO.getFullName());
        author.setCreatedAt(authorDTO.getCreatedAt());
        author.setArticles(authorDTO.getArticles());
        return author;
    }

    public AuthorDTO toDto(Author author){
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setFirstName(author.getFirstName());
        authorDTO.setLastName(author.getLastName());
        authorDTO.setFullName(author.getFullName());
        authorDTO.setCreatedAt(author.getCreatedAt());
        authorDTO.setArticles(author.getArticles());
        return authorDTO;
    }
}
