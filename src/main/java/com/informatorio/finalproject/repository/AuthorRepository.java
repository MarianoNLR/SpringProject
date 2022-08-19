package com.informatorio.finalproject.repository;


import com.informatorio.finalproject.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByFullNameContains(String fullName);

    List<Author> findByCreatedAtAfter(LocalDate after);
}
