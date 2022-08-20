package com.informatorio.finalproject.service;


import com.informatorio.finalproject.converter.ArticleConverter;
import com.informatorio.finalproject.dto.ArticleDTO;
import com.informatorio.finalproject.entity.Article;
import com.informatorio.finalproject.entity.Source;
import com.informatorio.finalproject.repository.ArticleRepository;
import com.informatorio.finalproject.repository.SourceRepository;
import com.informatorio.finalproject.util.CustomPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {


    private final ArticleRepository articleRepository;

    private final ArticleConverter articleConverter;

    private final SourceRepository sourceRepository;

    public ArticleService(ArticleRepository articleRepository,
                          ArticleConverter articleConverter,
                          SourceRepository sourceRepository) {
        this.articleRepository = articleRepository;
        this.articleConverter = articleConverter;
        this.sourceRepository = sourceRepository;
    }

    //Returning a custom page with articles. if it's necessary filter results
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
                .map(articleConverter::toSimpleAuthorDto)
                .collect(Collectors.toList()));
        customPage.setTotalElements(pageResult.getTotalElements());
        customPage.setTotalPages(pageResult.getTotalPages());
        customPage.setSize(pageResult.getSize());
        customPage.setPageNumber(pageResult.getNumber());


        return customPage;
    }

    //Get an article by id or return null if id doesn't exist
    public Article getArticle(Long id){
        Optional<Article> article = articleRepository.findById(id);
        return article.orElse(null);
    }

    //Method to create an article with sources associated
    public Article createArticle(ArticleDTO articleDTO){

        Article article = articleConverter.toEntity(articleDTO);

        List<Source> sources = articleDTO.getSources().stream()
                .map(id -> sourceRepository.findById(id.getId()))
                .filter(Optional::isPresent).map(Optional::get)
                .collect(Collectors.toList());

        for(Source source : sources){
            article.addSource(source);
        }
        return articleRepository.save(article);

    }

    //Update an article by put request
    public Article updateArticle(ArticleDTO articleDTO, Long id){
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()){
            Article articleAux = article.get();
            articleAux.setTitle(articleDTO.getTitle());
            articleAux.setDescription(articleDTO.getDescription());
            articleAux.setContent(articleDTO.getContent());
            articleAux.setUrl(articleDTO.getUrl());
            articleAux.setUrlToImage(articleDTO.getUrlToImage());
            articleAux.setPublishedAt(articleDTO.getPublishedAt());
            return articleRepository.save(articleAux);
        }

        return null;
    }

    //Update an article by patch request
    public Article patchArticle(ArticleDTO articleDTO, Long id){
        Article article = articleRepository.findById(id).orElse(null);

        if(StringUtils.hasLength(articleDTO.getTitle()))
            article.setTitle(articleDTO.getTitle());
        if(StringUtils.hasLength(articleDTO.getDescription()))
            article.setDescription(articleDTO.getDescription());
        if(StringUtils.hasLength(articleDTO.getContent()))
            article.setContent(articleDTO.getContent());
        if(StringUtils.hasLength(articleDTO.getUrl()))
            article.setUrl(articleDTO.getUrl());
        if(StringUtils.hasLength(articleDTO.getUrlToImage()))
            article.setUrlToImage(articleDTO.getUrlToImage());

        return articleRepository.save(article);
    }

    //Delete an article by id and needs to remove registers associated in intermediate table
    public boolean deleteArticle(Long id){
        Optional<Article> article = articleRepository.findById(id);
        if(article.isPresent()){
            List<Source> sources = new ArrayList<>();
            sources.addAll(article.get().getSources());
            article.get().getSources().removeAll(sources);
            articleRepository.deleteById(article.get().getId());
            return true;
        }

        return false;
    }


}




