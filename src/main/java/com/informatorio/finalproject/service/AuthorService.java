package com.informatorio.finalproject.service;

import com.informatorio.finalproject.converter.AuthorConverter;
import com.informatorio.finalproject.dto.AuthorDTO;
import com.informatorio.finalproject.entity.Author;
import com.informatorio.finalproject.repository.AuthorRepository;
import com.informatorio.finalproject.util.CustomPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorConverter authorConverter;



    @Autowired
    public AuthorService(AuthorRepository authorRepository, AuthorConverter authorConverter) {
        this.authorRepository = authorRepository;
        this.authorConverter = authorConverter;
    }

    public Author getAuthor(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        //Author authorAux = author.get();
        return author.get();
    }

    public CustomPage getAllAuthors(int page){
        PageRequest pageable = PageRequest.of(page, 5);
        Page<Author> pageResult;

        pageResult = authorRepository.findAll(pageable);
        CustomPage customPage = new CustomPage();
        customPage.setContent(pageResult.getContent().stream()
                .map(authorConverter::toDto)
                .collect(Collectors.toList()));
        customPage.setTotalElements(pageResult.getTotalElements());
        customPage.setTotalPages(pageResult.getTotalPages());
        customPage.setSize(pageResult.getSize());
        customPage.setPageNumber(pageResult.getNumber());

        return customPage;
    }

    public List<Author> getAuthorByFullName(String name){
        return authorRepository.findByFullNameContains(name);

    }

    public List<Author> getAuthorCreatedAfter(LocalDate date){
        return authorRepository.findByCreatedAtAfter(date);
    }



    public Author updateAuthor(AuthorDTO authorDTO, Long id){
        Optional<Author> author = authorRepository.findById(id);
        if(author.isPresent()){
            Author authorAux = author.get();
            authorAux.setFirstName(authorDTO.getFirstName());
            authorAux.setLastName(authorDTO.getLastName());
            this.setFullName(authorDTO);
            authorAux.setFullName(authorDTO.getFullName());
            return authorRepository.save(authorAux);

        }
        return null;
    }

    public Author createAuthor(AuthorDTO authorDTO){
        //this.setFullName(authorDTO);
        authorDTO.setFullName(authorDTO.getFirstName() + " " + authorDTO.getLastName());
        Author author = authorConverter.toEntity(authorDTO);
        return author = authorRepository.save(author);
    }

    private void setFullName(AuthorDTO authorDTO){
        authorDTO.setFullName(authorDTO.getFirstName() + " " + authorDTO.getLastName());
    }
}
