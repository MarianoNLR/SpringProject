package com.informatorio.finalproject.service;

import com.informatorio.finalproject.converter.AuthorConverter;
import com.informatorio.finalproject.dto.AuthorDTO;
import com.informatorio.finalproject.entity.Author;
import com.informatorio.finalproject.repository.ArticleRepository;
import com.informatorio.finalproject.repository.AuthorRepository;
import com.informatorio.finalproject.util.CustomPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorConverter authorConverter;

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private AuthorService authorService;


    @Test
    void when_call_method_createAuthor_save_ok(){
        AuthorDTO simulation = new AuthorDTO(1L,"Mariano", "Lotero", LocalDate.now());
        Author expected = new Author(1L,"Mariano", "Lotero", "Mariano Lotero" ,LocalDate.now());

        when(authorService.createAuthor(simulation)).thenReturn(expected);

        Author result = authorService.createAuthor(new AuthorDTO(1L, "Mariano", "Lotero", LocalDate.now()));
        assertEquals(simulation.getId(), result.getId());


    }
   @Test
    void when_call_get_authors_and_name_is_passed_list_with_coincides_is_returned(){
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(2L, "Mariano", "Lotero", "Mariano Lotero",LocalDate.now()));
        when(authorRepository.findByFullNameContains("Mariano")).thenReturn(authors);

        List<Author> response = authorService.getAuthorByFullName("Mariano");

        assertIterableEquals(authors, response);
    }

    @Test
    void when_method_get_author_return_an_author(){
        Author author = new Author();
        author.setId(1L);
        author.setFirstName("Mariano");
        author.setLastName("Lotero");
        author.setFullName("Mariano Lotero");
        author.setCreatedAt(LocalDate.now());

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Author response = authorService.getAuthor(1l);

        assertEquals(author,response);
    }
}