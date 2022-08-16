package com.informatorio.finalproject.controller;

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


import java.awt.print.Pageable;
import java.util.Collection;

@RestController
public class AuthorController {

    private AuthorRepository authorRepository;
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorRepository authorRepository, AuthorService authorService){
        this.authorRepository = authorRepository;
        this.authorService = authorService;
    }

    @PostMapping("/authors")
    public ResponseEntity<?> saveAuthor(@RequestBody AuthorDTO authorDTO){
        Author author = new Author(null, authorDTO.getFirstName(), authorDTO.getLastName(), authorDTO.getFullName(), authorDTO.getCreatedAt());
        return new ResponseEntity<>(authorRepository.save(author),HttpStatus.CREATED);
    }

    @GetMapping("/authors")
    public ResponseEntity<?>getAuthors(@RequestParam int page){
        PageRequest pageable = PageRequest.of(page, 3);
        Page<Author> pageResult = authorRepository.findAll(pageable);
        return new ResponseEntity<>(pageResult, HttpStatus.OK);

    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<?>getAuthors(@PathVariable Long id){
        return ResponseEntity.ok(authorRepository.findById(id));
    }

    @DeleteMapping(value = "/authors/{id}")
    public void deleteAuthor(@PathVariable Long id){
        authorRepository.deleteById(id);
    }



    /*@RequestMapping(value = "/authors", method = RequestMethod.GET)
    public @ResponseBody Iterable<Author> findAuthor(){
        return authorRepository.findAll();
    }*/
}
