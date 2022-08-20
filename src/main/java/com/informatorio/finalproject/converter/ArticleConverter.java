package com.informatorio.finalproject.converter;

import com.informatorio.finalproject.dto.ArticleAuthorSimpleDTO;
import com.informatorio.finalproject.dto.ArticleDTO;
import com.informatorio.finalproject.entity.Article;
import com.informatorio.finalproject.entity.Author;
import com.informatorio.finalproject.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ArticleConverter {


    private final AuthorRepository authorRepository;

    private final AuthorConverter authorConverter;

    private final SourceConverter sourceConverter;
    @Autowired
    public ArticleConverter(AuthorRepository authorRepository, AuthorConverter authorConverter, SourceConverter sourceConverter) {
        this.authorRepository = authorRepository;
        this.authorConverter = authorConverter;
        this.sourceConverter = sourceConverter;
    }

    public Article toEntity(ArticleDTO articleDTO){
        Article article = new Article();
        article.setId(articleDTO.getId());
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setDescription(articleDTO.getDescription());
        article.setUrl(articleDTO.getUrl());
        article.setUrlToImage(articleDTO.getUrlToImage());
        article.setPublishedAt(articleDTO.getPublishedAt());
        Optional<Author> authorOptional = authorRepository.findById(articleDTO.getAuthor().getId());
        article.setAuthor(authorOptional.get());

        return article;
    }

    public ArticleDTO toDto(Article article){
        ArticleDTO articleDTO = new ArticleDTO();

        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setDescription(article.getDescription());
        articleDTO.setUrl(article.getUrl());
        articleDTO.setUrlToImage(article.getUrlToImage());
        articleDTO.setPublishedAt(article.getPublishedAt());
        Optional<Author> authorOptional = authorRepository.findById(article.getAuthor().getId());
        articleDTO.setAuthor(authorConverter.toDto(authorOptional.get()));
        articleDTO.setSources(article.getSources().stream()
                .map(sourceConverter::toDto)
                .collect(Collectors.toList()));

        return articleDTO;
    }

    public ArticleAuthorSimpleDTO toSimpleAuthorDto(Article article){
        ArticleAuthorSimpleDTO articleSimpleDTO = new ArticleAuthorSimpleDTO();

        articleSimpleDTO.setId(article.getId());
        articleSimpleDTO.setTitle(article.getTitle());
        articleSimpleDTO.setContent(article.getContent());
        articleSimpleDTO.setDescription(article.getDescription());
        articleSimpleDTO.setUrl(article.getUrl());
        articleSimpleDTO.setUrlToImage(article.getUrlToImage());
        articleSimpleDTO.setPublishedAt(article.getPublishedAt());
        Optional<Author> authorOptional = authorRepository.findById(article.getAuthor().getId());
        articleSimpleDTO.setFullName(authorOptional.get().getFullName());
        articleSimpleDTO.setSources(article.getSources().stream()
                .map(sourceConverter::toDto)
                .collect(Collectors.toList()));

        return articleSimpleDTO;

    }

}
