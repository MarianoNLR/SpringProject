package com.informatorio.finalproject.controller;

import com.informatorio.finalproject.converter.AuthorConverter;
import com.informatorio.finalproject.dto.AuthorDTO;
import com.informatorio.finalproject.entity.Author;
import com.informatorio.finalproject.repository.AuthorRepository;
import com.informatorio.finalproject.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

    private AuthorRepository authorRepository;
    private final AuthorService authorService;

    private final AuthorConverter authorConverter;

    @Autowired
    public AuthorController(AuthorRepository authorRepository, AuthorService authorService, AuthorConverter authorConverter){
        this.authorRepository = authorRepository;
        this.authorService = authorService;
        this.authorConverter = authorConverter;
    }

    @PostMapping("/authors")
    public ResponseEntity<?> createAuthor(@Valid @RequestBody AuthorDTO authorDTO){
        Author author = authorConverter.toEntity(authorDTO);
        author = authorRepository.save(author);
        return new ResponseEntity<>(authorConverter.toDto(author), HttpStatus.CREATED);
    }

    @GetMapping("/authors")
    public ResponseEntity<?>getAuthors(@RequestParam int page){
        PageRequest pageable = PageRequest.of(page, 3);
        Page<Author> pageResult = authorRepository.findAll(pageable);

        Map<String, Object> customPage = new HashMap<>();
        customPage.put("content", pageResult.getContent().stream()
                .map(author -> authorConverter.toDto(author))
                .collect(Collectors.toList()));
        customPage.put("page", pageResult.getNumber());
        customPage.put("size", pageResult.getSize());
        customPage.put("totalElements", pageResult.getTotalElements());

        return new ResponseEntity<>(customPage, HttpStatus.OK);

    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<?>findById(@PathVariable Long id){
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()){
            return new ResponseEntity<>(authorConverter.toDto(author.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/authors/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id){
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()){
            authorRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

   /*@PutMapping("/authors")
    public Author updateAuthor(@RequestBody AuthorDTO authorDTO){
        return authorService.updateAuthor(authorDTO);
    }*/



    /*@RequestMapping(value = "/authors", method = RequestMethod.GET)
    public @ResponseBody Iterable<Author> findAuthor(){
        return authorRepository.findAll();
    }*/
}
