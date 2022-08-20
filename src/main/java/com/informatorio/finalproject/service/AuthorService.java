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
import org.springframework.util.StringUtils;

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

    //Get author by id coming from parameter
    public Author getAuthor(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        //Author authorAux = author.get();
        return author.get();
    }

    //Create a custom page with a list of authors and returns it
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

    //Getting authors which contains the string coming through parameters
    public List<Author> getAuthorByFullName(String name){
        return authorRepository.findByFullNameContains(name);

    }

    //Getting a list of authors which were created after a date coming in parameters
    public List<Author> getAuthorCreatedAfter(LocalDate date){
        return authorRepository.findByCreatedAtAfter(date);
    }


    //Updating an author by put request
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

    //Updating an author by patch request
    public Author patchAuthor(AuthorDTO authorDTO, Long id){
        Author author = authorRepository.findById(id).orElse(null);

        if(StringUtils.hasLength(authorDTO.getFirstName())){
            author.setFirstName(authorDTO.getFirstName());
        }
        if(StringUtils.hasLength(authorDTO.getLastName())){
            author.setLastName(authorDTO.getLastName());
        }
        author.setFullName(author.getFirstName() + " " + author.getLastName());

        return authorRepository.save(author);
    }

    //Save an author from an authorDTO
    public Author createAuthor(AuthorDTO authorDTO){
        //this.setFullName(authorDTO);
        authorDTO.setFullName(authorDTO.getFirstName() + " " + authorDTO.getLastName());
        Author author = authorConverter.toEntity(authorDTO);
        return author = authorRepository.save(author);
    }

    //Method to set full name of an author by joining first name and last name
    private void setFullName(AuthorDTO authorDTO){
        authorDTO.setFullName(authorDTO.getFirstName() + " " + authorDTO.getLastName());
    }
}
