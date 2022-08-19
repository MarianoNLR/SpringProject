package com.informatorio.finalproject.converter;

import com.informatorio.finalproject.dto.AuthorDTO;
import com.informatorio.finalproject.entity.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorConverterTest {

    @InjectMocks
    private AuthorConverter authorConverter;

    @Test
    void toEntity() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1L);
        authorDTO.setFirstName("Mariano");
        authorDTO.setLastName("Lotero");
        authorDTO.setFullName("Mariano Lotero");
        authorDTO.setCreatedAt(LocalDate.now());

        Author author = authorConverter.toEntity(authorDTO);

        assertEquals(authorDTO.getId(), author.getId());
    }

    @Test
    void toDto() {
        Author author = new Author();

        author.setId(1L);
        author.setFirstName("Mariano");
        author.setLastName("Lotero");
        author.setFullName("Mariano Lotero");
        author.setCreatedAt(LocalDate.now());

        AuthorDTO authorDTO = authorConverter.toDto(author);

        assertEquals(author.getId(), authorDTO.getId());
        assertEquals(author.getFirstName(), authorDTO.getFirstName());
        assertEquals(author.getLastName(), authorDTO.getLastName());
        assertEquals(author.getCreatedAt(), authorDTO.getCreatedAt());
    }
}