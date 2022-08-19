package com.informatorio.finalproject.service;

import com.informatorio.finalproject.converter.AuthorConverter;
import com.informatorio.finalproject.dto.AuthorDTO;
import com.informatorio.finalproject.entity.Author;
import com.informatorio.finalproject.repository.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private AuthorRepository authorRepository;
    private AuthorConverter authorConverter;




    public AuthorService(AuthorRepository authorRepository, AuthorConverter authorConverter) {
        this.authorRepository = authorRepository;
        this.authorConverter = authorConverter;
    }

    public AuthorDTO getAuthor(Long id){
        return authorRepository.findById(id)
                .map(author -> authorConverter.toDto(author)).get();
    }

    public Map<String, Object> getAllAuthors(int page, String name, LocalDate after){
        PageRequest pageable = PageRequest.of(page, 5);
        Page<Author> pageResult = authorRepository.findAll(pageable);
        if (after == null){
            after = LocalDate.ofEpochDay(0);
        }
        Map<String, Object> customPage = new HashMap<>();
        LocalDate finalAfter = after;
        customPage.put("content", pageResult.getContent().stream()
                .filter(author -> author.getFullName().matches("(?i).*"+name+".*"))
                .filter(author -> author.getCreatedAt().isAfter(finalAfter))
                .map(author -> authorConverter.toDto(author))
                .collect(Collectors.toList()));
        customPage.put("page", pageResult.getNumber());
        customPage.put("size", pageResult.getSize());
        customPage.put("totalElements", pageResult.getTotalElements());
        return customPage;
    }



    public AuthorDTO updateAuthor(AuthorDTO authorDTO, Long id){
        Optional<Author> author = authorRepository.findById(id);
        if(author.isPresent()){
            Author authorAux = author.get();
            authorAux.setFirstName(authorDTO.getFirstName());
            authorAux.setLastName(authorDTO.getLastName());
            this.setFullName(authorDTO);
            authorAux.setFullName(authorDTO.getFullName());
            authorRepository.save(authorAux);
            return authorConverter.toDto(authorAux);
        }
        return null;
    }

    public AuthorDTO createAuthor(AuthorDTO authorDTO){
        this.setFullName(authorDTO);
        Author author = authorConverter.toEntity(authorDTO);
        author = authorRepository.save(author);
        authorDTO = authorConverter.toDto(author);
        return authorDTO;
    }

    private void setFullName(AuthorDTO authorDTO){
        authorDTO.setFullName(authorDTO.getFirstName() + " " + authorDTO.getLastName());
    }
}
