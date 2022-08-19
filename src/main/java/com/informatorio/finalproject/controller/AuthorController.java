package com.informatorio.finalproject.controller;

import com.informatorio.finalproject.converter.AuthorConverter;
import com.informatorio.finalproject.dto.AuthorDTO;
import com.informatorio.finalproject.entity.Author;
import com.informatorio.finalproject.repository.AuthorRepository;
import com.informatorio.finalproject.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RequestMapping("/api/v1")
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

        return new ResponseEntity<>(authorService.createAuthor(authorDTO), HttpStatus.CREATED);
    }

    @GetMapping("/authors")
    public ResponseEntity<?>getAuthors(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(required = false, defaultValue = "") String name,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate after){
        return new ResponseEntity<>(authorService.getAllAuthors(page, name, after), HttpStatus.OK);



    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<?>findById(@PathVariable Long id){
        //Optional<Author> author = authorService.getAuthor(id);
        AuthorDTO authorDTO = authorService.getAuthor(id);
        if (authorDTO != null){
            return new ResponseEntity<>(authorDTO, HttpStatus.OK);
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

   @PutMapping("/authors/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable Long id ,@RequestBody @Valid AuthorDTO authorDTO){
        AuthorDTO authorDTOAux = authorService.updateAuthor(authorDTO, id);

        if(authorDTOAux != null){
            return new ResponseEntity<>(authorDTOAux, HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }



    /*@RequestMapping(value = "/authors", method = RequestMethod.GET)
    public @ResponseBody Iterable<Author> findAuthor(){
        return authorRepository.findAll();
    }*/
}
