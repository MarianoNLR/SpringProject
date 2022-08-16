package com.informatorio.finalproject.converter;

import com.informatorio.finalproject.dto.ArticleDTO;
import com.informatorio.finalproject.entity.Article;
import com.informatorio.finalproject.entity.Author;
import com.informatorio.finalproject.repository.AuthorRepository;
import com.informatorio.finalproject.repository.SourceRepository;
import org.springframework.core.convert.converter.Converter;

import java.util.Optional;

public class ArticleDtoToArticleEntityConverter implements Converter<ArticleDTO, Article> {

    private final AuthorRepository authorRepository;

    private final SourceRepository sourceRepository;

    public ArticleDtoToArticleEntityConverter(AuthorRepository authorRepository, SourceRepository sourceRepository) {
        this.authorRepository = authorRepository;
        this.sourceRepository = sourceRepository;
    }

    @Override
    public Article convert(ArticleDTO articleDTO){
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
}
