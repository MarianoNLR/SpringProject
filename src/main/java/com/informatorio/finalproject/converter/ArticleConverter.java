package com.informatorio.finalproject.converter;

import com.informatorio.finalproject.dto.ArticleDTO;
import com.informatorio.finalproject.entity.Article;
import com.informatorio.finalproject.entity.Author;
import com.informatorio.finalproject.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class ArticleConverter {

    @Autowired
    private final AuthorRepository authorRepository;

    public ArticleConverter(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Article toEntity(ArticleDTO articleDTO){
        Article article = new Article();
        article.setId(articleDTO.getId());
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setDescription(articleDTO.getDescription());
        article.setUrl(articleDTO.getUrl());
        article.setUrlToImage(articleDTO.getUrlToImage());
        Optional<Author> authorOptional = authorRepository.findById(article.getAuthor().getId());
        article.setAuthor(authorOptional.get());
        article.setSources(articleDTO.getSources());

        return article;
    }

    public ArticleDTO toDto(Article article){
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
