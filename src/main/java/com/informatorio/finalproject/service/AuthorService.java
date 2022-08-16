package com.informatorio.finalproject.service;

import com.informatorio.finalproject.dto.AuthorDTO;
import com.informatorio.finalproject.entity.Author;
import com.informatorio.finalproject.entity.Source;
import com.informatorio.finalproject.repository.ArticleRepository;
import com.informatorio.finalproject.repository.AuthorRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private AuthorRepository authorRepository;



    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /*public AuthorDTO getAuthor(Long id){
        return authorRepository.findById(id)
                .map(this::toDTO).get();
    }*/

    /*public List<AuthorDTO> getAllAuthors(int page){
        PageRequest pageable = PageRequest.of(page, 3);

        return authorRepository.findAll(pageable)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }*/



    /*public Author updateAuthor(AuthorDTO authorDTO){
        Author author = this.toEntity(authorDTO);
        authorRepository.save(author);
        return author;
    }*/
}
