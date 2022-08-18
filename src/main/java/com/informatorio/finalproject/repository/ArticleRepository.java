package com.informatorio.finalproject.repository;

import com.informatorio.finalproject.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByTitleContainingOrDescriptionContainingOrContentContainingOrAuthorFullNameContainingAndPublishedAtIsNotNull(Pageable page, String title, String description, String content, String fullName);
}
