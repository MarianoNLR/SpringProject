package com.informatorio.finalproject.repository;

import com.informatorio.finalproject.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Page<Author> findByFullNameContainsAndCreatedAtAfter(Pageable page, String fullName, LocalDate after);
}
