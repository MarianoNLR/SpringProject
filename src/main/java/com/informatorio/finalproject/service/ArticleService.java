package com.informatorio.finalproject.service;


import com.informatorio.finalproject.converter.ArticleConverter;
import com.informatorio.finalproject.converter.AuthorConverter;
import com.informatorio.finalproject.converter.SourceConverter;
import com.informatorio.finalproject.dto.ArticleDTO;
import com.informatorio.finalproject.dto.AuthorDTO;
import com.informatorio.finalproject.dto.SourceDTO;
import com.informatorio.finalproject.entity.Article;
import com.informatorio.finalproject.entity.Source;
import com.informatorio.finalproject.repository.ArticleRepository;
import com.informatorio.finalproject.repository.AuthorRepository;
import com.informatorio.finalproject.repository.SourceRepository;
import com.informatorio.finalproject.util.CustomPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {


    private ArticleRepository articleRepository;

    private AuthorRepository authorRepository;

    private AuthorService authorService;
    private ArticleConverter articleConverter;
    private AuthorConverter authorConverter;

    private SourceRepository sourceRepository;

    private SourceConverter sourceConverter;
    public ArticleService(ArticleRepository articleRepository, AuthorRepository authorRepository,
                          AuthorService authorService, ArticleConverter articleConverter,
                          AuthorConverter authorConverter, SourceRepository sourceRepository,
                          SourceConverter sourceConverter) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.authorService = authorService;
        this.articleConverter = articleConverter;
        this.authorConverter = authorConverter;
        this.sourceRepository = sourceRepository;
        this.sourceConverter = sourceConverter;
    }

    public CustomPage getArticles(int page, String q){
        PageRequest pageable = PageRequest.of(page, 5);
        Page<Article> pageResult;
        if (!q.isBlank()){
            pageResult = articleRepository.findByTitleContainingOrDescriptionContainingOrContentContainingOrAuthorFullNameContainingAndPublishedAtIsNotNull(pageable, q, q, q, q);
        }else{
            pageResult = articleRepository.findAll(pageable);
        }

        CustomPage customPage = new CustomPage();
        customPage.setContent(pageResult.getContent().stream()
                .map(article -> articleConverter.toDto(article))
                .collect(Collectors.toList()));
        customPage.setTotalElements(pageResult.getTotalElements());
        customPage.setTotalPages(pageResult.getTotalPages());
        customPage.setSize(pageResult.getSize());
        customPage.setPageNumber(pageResult.getNumber());


        return customPage;
    }

    public ArticleDTO getArticle(Long id){
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()){
            ArticleDTO articleDTO = articleConverter.toDto(article.get());
            return articleDTO;
        }
        return null;
    }

    public ArticleDTO createArticle(ArticleDTO articleDTO){

        Article article = articleConverter.toEntity(articleDTO);

        List<Source> sources = articleDTO.getSources().stream()
                .map(id -> sourceRepository.findById(id.getId()))
                .filter(Optional::isPresent).map(Optional::get)
                .collect(Collectors.toList());

        for(Source source : sources){
            article.addSource(source);
        }
        articleRepository.save(article);
        return articleConverter.toDto(article);
    }

    public ArticleDTO updateArticle(ArticleDTO articleDTO, Long id){
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()){
            Article articleAux = article.get();
            articleAux.setTitle(articleDTO.getTitle());
            articleAux.setDescription(articleDTO.getDescription());
            articleAux.setContent(articleDTO.getContent());
            articleAux.setUrl(articleDTO.getUrl());
            articleAux.setUrlToImage(articleDTO.getUrlToImage());
            articleAux.setPublishedAt(articleDTO.getPublishedAt());
            articleRepository.save(articleAux);
            articleDTO = articleConverter.toDto(articleAux);
            return articleDTO;
        }

        return null;
    }

    public boolean deleteArticle(Long id){
        Optional<Article> article = articleRepository.findById(id);
        if(article.isPresent()){
            for (Source source :  article.get().getSources()){
                article.get().getSources().remove(source);
            }
            articleRepository.delete(article.get());
            return true;
        }
        return false;


    }


}




