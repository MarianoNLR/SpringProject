package com.informatorio.finalproject.service;


import com.informatorio.finalproject.dto.ArticleDTO;
import com.informatorio.finalproject.entity.Article;
import com.informatorio.finalproject.entity.Author;
import com.informatorio.finalproject.repository.ArticleRepository;
import com.informatorio.finalproject.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {


    private ArticleRepository articleRepository;

    private AuthorRepository authorRepository;

    private AuthorService authorService;

    public ArticleService(ArticleRepository articleRepository, AuthorRepository authorRepository, AuthorService authorService) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.authorService = authorService;
    }

    public List<ArticleDTO> getArticles(){

        return articleRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ArticleDTO toDTO(Article article){
        ArticleDTO articleDTO = new ArticleDTO();;
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setDescription(article.getDescription());
        articleDTO.setUrl(article.getUrl());
        articleDTO.setUrlToImage(article.getUrlToImage());
        articleDTO.setPublishedAt(article.getPublishedAt());
        Optional<Author> authorOptional = authorRepository.findById(article.getAuthor().getId());
        articleDTO.setAuthor(authorOptional.get().getFullName());
        articleDTO.setSources(article.getSources());
        return articleDTO;
    }
}
